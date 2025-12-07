package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemInput;
import io.github.dudupuci.infrastructure.persistence.facade.mensagens.MensagemFacade;
import io.github.dudupuci.infrastructure.security.SimpleSessionManager;
import io.github.dudupuci.infrastructure.web.controllers.apidocs.MensagensControllerAPI;
import io.github.dudupuci.infrastructure.web.dtos.request.mensagem.EnviarMensagemRequest;
import io.github.dudupuci.infrastructure.web.dtos.response.mensagem.EnviarMensagemApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

/**
 * Controller para mensagens com processamento SÍNCRONO via fila FIFO
 */
@RestController
@RequestMapping("/mensagens")
@CrossOrigin(origins = "*")
public class MensagensController implements MensagensControllerAPI {

    private static final Logger logger = LoggerFactory.getLogger(MensagensController.class);

    private final MensagemFacade mensagemFacade;
    private final SimpleSessionManager sessionManager;

    public MensagensController(
            MensagemFacade mensagemFacade,
            SimpleSessionManager sessionManager
    ) {
        this.mensagemFacade = mensagemFacade;
        this.sessionManager = sessionManager;
    }

    /**
     * POST /mensagens
     * Envia uma mensagem com PROCESSAMENTO SÍNCRONO (fila + processamento na mesma requisição)
     */
    @PostMapping
    public ResponseEntity<?> enviarMensagem(
            @RequestBody EnviarMensagemRequest request,
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @RequestHeader(value = "X-Remetente-Id", required = false) Long remetenteIdHeader
    ) {
        try {
            // Identifica o remetente (prioridade: header > sessão > request)
            Long remetenteId = remetenteIdHeader != null ? remetenteIdHeader :
                    (sessionManager.isValidSession(sessionId) ? sessionManager.getIdUsuario(sessionId) : 1L);

            logger.info("Recebendo mensagem de remetente {} para destinatário {}",
                    remetenteId, request.destinatarioId());

            // Cria o input e delega toda a validação para o caso de uso
            EnviarMensagemInput input = request.toApplicationInput(remetenteId);

            // O caso de uso é responsável por validar remetente, destinatário e regras de negócio
            EnviarMensagemApiResponse apiResponse = EnviarMensagemApiResponse.toApiResponse(
                    mensagemFacade.enviarMensagem(input)
            );

            return ResponseEntity.ok(apiResponse);

        } catch (IllegalArgumentException e) {
            logger.warn("Erro de validação ao enviar mensagem: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem", e);
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
        return null;
    }

    /**
     * GET /mensagens/destinatario/{destinatarioId}
     * Busca mensagens para um destinatário (para polling no frontend)
     */
    @GetMapping("/destinatario/{destinatarioId}")
    public ResponseEntity<?> buscarMensagensDestinatario(
            @PathVariable Long destinatarioId
    ) {
        return null;
    }

    /**
     * GET /mensagens/fila/estatisticas
     * Retorna estatísticas da fila de processamento
     */
    @GetMapping("/fila/estatisticas")
    public ResponseEntity<?> obterEstatisticasFila() {
        return null;
    }

    /**
     * GET /mensagens/fila/tamanho
     * Retorna o tamanho atual da fila
     */
    @GetMapping("/fila/tamanho")
    public ResponseEntity<Integer> obterTamanhoFila() {
        return null;
    }
}