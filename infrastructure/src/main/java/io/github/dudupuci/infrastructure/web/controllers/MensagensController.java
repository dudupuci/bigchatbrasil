package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemInput;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemOutput;
import io.github.dudupuci.infrastructure.queue.FilaMensagens;
import io.github.dudupuci.infrastructure.queue.ProcessadorMensagens;
import io.github.dudupuci.infrastructure.security.RequiresAuth;
import io.github.dudupuci.infrastructure.security.SessionInfo;
import io.github.dudupuci.infrastructure.web.controllers.apidocs.MensagensControllerAPI;
import io.github.dudupuci.infrastructure.web.dtos.request.mensagem.EnviarMensagemRequest;
import io.github.dudupuci.infrastructure.web.dtos.response.mensagem.EnviarMensagemApiResponse;
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

    public MensagensController(
            FilaMensagens filaMensagens,
            ProcessadorMensagens processadorMensagens
    ) {
        this.filaMensagens = filaMensagens;
        this.processadorMensagens = processadorMensagens;
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
            @RequestBody EnviarMensagemRequest request,
            HttpServletRequest httpRequest
    ) {
        try {
            // Pega o ID do usuário autenticado da sessão
            // SessionInterceptor já validou o X-Session-Id e colocou sessionInfo aqui
            SessionInfo sessionInfo = (SessionInfo) httpRequest.getAttribute("sessionInfo");
            Long remetenteId = sessionInfo.idUsuario();

            logger.info("📨 Recebendo mensagem de {} (tipo: {}) para destinatário {} | Prioridade: {}",
                    remetenteId,
                    sessionInfo.tipoUsuario(),
                    request.destinatarioId(),
                    request.prioridade());

            // Cria o input com o remetente autenticado
            EnviarMensagemInput input = request.toApplicationInput(remetenteId);

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
            @PathVariable UUID conversaId
    ) {
        try {
            // TODO: Implementar quando tiver caso de uso de listar mensagens
            logger.info("📋 Listando mensagens da conversa: {}", conversaId);
            return ResponseEntity.ok("Implementar ListarMensagensUseCase");
        } catch (Exception e) {
            logger.error("Erro ao listar mensagens da conversa {}", conversaId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar mensagens: " + e.getMessage());
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