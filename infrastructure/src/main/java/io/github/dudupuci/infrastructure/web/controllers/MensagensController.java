package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.application.usecases.mensagem.criarconversa.CriarConversaInput;
import io.github.dudupuci.application.usecases.mensagem.criarconversa.CriarConversaOutput;
import io.github.dudupuci.application.usecases.mensagem.criarconversa.CriarConversaUseCase;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemInput;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemOutput;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensInput;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensOutput;
import io.github.dudupuci.application.usecases.mensagem.listarconversas.ListarConversasInput;
import io.github.dudupuci.application.usecases.mensagem.listarconversas.ListarConversasOutput;
import io.github.dudupuci.domain.enums.TipoUsuario;
import io.github.dudupuci.infrastructure.persistence.facade.mensagens.MensagemFacade;
import io.github.dudupuci.infrastructure.queue.FilaMensagens;
import io.github.dudupuci.infrastructure.queue.ProcessadorMensagens;
import io.github.dudupuci.infrastructure.security.annotations.RequiresAuth;
import io.github.dudupuci.infrastructure.security.SessionInfo;
import io.github.dudupuci.infrastructure.web.controllers.apidocs.MensagensControllerAPI;
import io.github.dudupuci.infrastructure.web.dtos.request.mensagem.CriarConversaApiRequest;
import io.github.dudupuci.infrastructure.web.dtos.request.mensagem.EnviarMensagemApiRequest;
import io.github.dudupuci.infrastructure.web.dtos.response.mensagem.CriarConversaApiResponse;
import io.github.dudupuci.infrastructure.web.dtos.response.mensagem.EnviarMensagemApiResponse;
import io.github.dudupuci.infrastructure.web.dtos.response.mensagem.ListarConversasApiResponse;
import io.github.dudupuci.infrastructure.web.dtos.response.mensagem.ListarMensagensApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

/**
 * Controller para mensagens com processamento SÍNCRONO via fila FIFO com priorização
 *
 * PARTE 1: Fila básica FIFO + processamento síncrono
 * PARTE 2: Fila com priorização (Normal/Urgente) + anti-starvation + status detalhado
 */
@RestController
@RequestMapping("/mensagens")
@CrossOrigin(origins = "*")
@RequiresAuth
public class MensagensController implements MensagensControllerAPI {

    private static final Logger logger = LoggerFactory.getLogger(MensagensController.class);

    private final FilaMensagens filaMensagens;
    private final ProcessadorMensagens processadorMensagens;
    private final MensagemFacade mensagemFacade;
    private final CriarConversaUseCase criarConversaUseCase;

    public MensagensController(
            FilaMensagens filaMensagens,
            ProcessadorMensagens processadorMensagens,
            MensagemFacade mensagemFacade,
            CriarConversaUseCase criarConversaUseCase
    ) {
        this.filaMensagens = filaMensagens;
        this.processadorMensagens = processadorMensagens;
        this.mensagemFacade = mensagemFacade;
        this.criarConversaUseCase = criarConversaUseCase;
    }

    /**
     * POST /mensagens
     * Envia uma mensagem com PROCESSAMENTO SÍNCRONO (fila + processamento na mesma requisição)
     * Fluxo:
     * 1. Enfileira mensagem com priorização
     * 2. Desenfileira imediatamente (respeita prioridade)
     * 3. Processa na mesma requisição
     * 4. Retorna resultado
     */
    @PostMapping
    public ResponseEntity<?> enviarMensagem(
            @RequestBody EnviarMensagemApiRequest request,
            HttpServletRequest httpRequest
    ) {
        try {
            // Pega o ID do usuário autenticado da sessão
            // SessionInterceptor já validou o X-Session-Id e colocou sessionInfo aqui
            SessionInfo sessionInfo = (SessionInfo) httpRequest.getAttribute("sessionInfo");
            Long remetenteId = sessionInfo.idUsuario();

            logger.info("📨 Recebendo mensagem de {} (tipo: {}) para destinatário {} (tipo: {}) | Prioridade: {}",
                    remetenteId,
                    sessionInfo.tipoUsuario(),
                    request.destinatarioId(),
                    request.tipoDestinatario(),
                    request.prioridade());

            // Cria o input com o remetente autenticado (ID + Tipo)
            EnviarMensagemInput input = request.toApplicationInput(remetenteId, sessionInfo.tipoUsuario());

            // ✅ PARTE 1 & 2: ENFILEIRA com priorização
            UUID idFila = filaMensagens.enfileirar(input);

            logger.info("🔵 Mensagem enfileirada: {} | Fila atual: {} mensagens",
                    idFila, filaMensagens.tamanho());

            // ✅ PARTE 1 & 2: DESENFILEIRA (respeita priorização e anti-starvation)
            FilaMensagens.ItemFila item = filaMensagens.desenfileirar();

            if (item == null) {
                logger.error("❌ Erro: mensagem foi enfileirada mas não pode ser desenfileirada");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro no processamento da fila");
            }

            logger.info("🟢 Processando mensagem: {} | Prioridade: {}", item.id, item.prioridade);

            // ✅ PARTE 1 & 2: PROCESSA SINCRONAMENTE
            EnviarMensagemOutput output = processadorMensagens.processar(item);

            // Marca como processada
            filaMensagens.marcarProcessada(idFila, output);

            logger.info("✅ Mensagem enviada com sucesso: {} | Status: {} | Fila restante: {}",
                    output.id(), output.status(), filaMensagens.tamanho());

            EnviarMensagemApiResponse apiResponse = EnviarMensagemApiResponse.toApiResponse(output);

            return ResponseEntity.ok(apiResponse);

        } catch (IllegalArgumentException e) {
            logger.warn("⚠️ Erro de validação: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            logger.error("❌ Erro ao enviar mensagem", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao enviar mensagem: " + e.getMessage());
        }
    }

    /**
     * GET /mensagens/conversa/{conversaId}
     * Lista todas as mensagens de uma conversa (essencial para o chat funcionar)
     */
    @GetMapping("/conversa/{conversaId}")
    public ResponseEntity<?> listarMensagensDaConversa(
            @PathVariable UUID conversaId,
            HttpServletRequest httpRequest
    ) {
        try {
            // Pega o usuário autenticado da sessão
            SessionInfo sessionInfo = (SessionInfo) httpRequest.getAttribute("sessionInfo");
            Long usuarioId = sessionInfo.idUsuario();

            logger.info("📋 Listando mensagens da conversa: {} | Usuário: {}", conversaId, usuarioId);

            // Cria o input com validação de permissão
            ListarMensagensInput input = new ListarMensagensInput(conversaId, usuarioId);

            // Executa o caso de uso
            ListarMensagensOutput output = mensagemFacade.listarMensagens(input);

            // Converte para response da API
            ListarMensagensApiResponse response = ListarMensagensApiResponse.toApiResponse(output);

            logger.info("✅ Listadas {} mensagens da conversa {}", output.total(), conversaId);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            logger.warn("⚠️ Erro de validação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            logger.error("❌ Erro ao listar mensagens da conversa {}", conversaId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar mensagens: " + e.getMessage());
        }
    }

    /**
     * POST /mensagens/conversas
     * Cria uma conversa entre dois usuários (conversaId determinístico)
     * Chamado ao adicionar um novo contato, antes de enviar mensagens
     */
    @PostMapping("/conversas")
    public ResponseEntity<?> criarConversa(
            @RequestBody CriarConversaApiRequest request,
            HttpServletRequest httpRequest
    ) {
        try {
            // Pega o usuário autenticado
            SessionInfo sessionInfo = (SessionInfo) httpRequest.getAttribute("sessionInfo");

            logger.info("📝 Criando conversa:");
            logger.info("   → Usuário logado: {} (tipo: {})", sessionInfo.idUsuario(), sessionInfo.tipoUsuario());
            logger.info("   → Destinatário REQUEST: {} (tipo: {})", request.destinatarioId(), request.tipoDestinatario());

            // Cria o input
            CriarConversaInput input = new CriarConversaInput(
                    sessionInfo.idUsuario(),
                    sessionInfo.tipoUsuario(),
                    request.destinatarioId(),
                    TipoUsuario.valueOf(request.tipoDestinatario())
            );

            logger.info("   → Input criado - Usuario1: {}, Usuario2: {}", input.usuarioId1(), input.usuarioId2());

            // Executa o caso de uso
            CriarConversaOutput output = criarConversaUseCase.execute(input);

            logger.info("✅ Conversa criada: {}", output.conversaId());

            CriarConversaApiResponse response = CriarConversaApiResponse.toApiResponse(output);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            logger.warn("⚠️ Erro de validação: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            logger.error("❌ Erro ao criar conversa", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar conversa: " + e.getMessage());
        }
    }

    /**
     * GET /mensagens/conversas
     * Lista todas as conversas do usuário logado
     */
    @GetMapping("/conversas")
    public ResponseEntity<?> listarConversas(HttpServletRequest httpRequest) {
        try {
            // Pega o usuário autenticado da sessão
            SessionInfo sessionInfo = (SessionInfo) httpRequest.getAttribute("sessionInfo");

            logger.info("📋 Listando conversas do usuário: {} (tipo: {})",
                    sessionInfo.idUsuario(), sessionInfo.tipoUsuario());

            // Cria o input
            ListarConversasInput input = new ListarConversasInput(
                    sessionInfo.idUsuario(),
                    sessionInfo.tipoUsuario()
            );

            // Executa o caso de uso
            ListarConversasOutput output = mensagemFacade.listarConversas(input);

            // Converte para response da API
            ListarConversasApiResponse response = ListarConversasApiResponse.toApiResponse(output);

            logger.info("✅ Listadas {} conversas do usuário {}", output.total(), sessionInfo.idUsuario());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("❌ Erro ao listar conversas", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar conversas: " + e.getMessage());
        }
    }

    /**
     * GET /mensagens/destinatario/{destinatarioId}
     * Busca mensagens para um destinatário (para polling no frontend)
     */
    @GetMapping("/destinatario/{destinatarioId}")
    public ResponseEntity<?> buscarMensagensDestinatario(
            @PathVariable Long destinatarioId,
            HttpServletRequest httpRequest
    ) {
        try {
            SessionInfo sessionInfo = (SessionInfo) httpRequest.getAttribute("sessionInfo");

            // Valida que está buscando suas próprias mensagens
            if (!sessionInfo.idUsuario().equals(destinatarioId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Você só pode buscar suas próprias mensagens");
            }

            logger.info("📬 Buscando mensagens para destinatário: {}", destinatarioId);
            // TODO: Implementar quando tiver caso de uso de buscar mensagens
            return ResponseEntity.ok("Implementar BuscarMensagensUseCase");
        } catch (Exception e) {
            logger.error("Erro ao buscar mensagens do destinatário {}", destinatarioId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar mensagens: " + e.getMessage());
        }
    }


}