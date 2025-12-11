package io.github.dudupuci.infrastructure.queue;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemOutput;
import io.github.dudupuci.infrastructure.persistence.facade.mensagens.MensagemFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Processador de mensagens SÍNCRONO
 * Processa a mensagem imediatamente na mesma requisição
 *
 * Parte 1: Processamento síncrono básico (fila + processamento na mesma requisição)
 * Parte 2: Mantém processamento síncrono mas com fila completa e priorização
 */
@Component
public class ProcessadorMensagens {

    private static final Logger logger = LoggerFactory.getLogger(ProcessadorMensagens.class);

    private final MensagemFacade mensagemFacade;

    public ProcessadorMensagens(MensagemFacade mensagemFacade) {
        this.mensagemFacade = mensagemFacade;
    }

    /**
     * Processa uma mensagem da fila SINCRONAMENTE
     * Retorna o resultado imediatamente para a requisição HTTP
     */
    public EnviarMensagemOutput processar(FilaMensagens.ItemFila item) {
        logger.info("Processando mensagem {} | Prioridade: {} | Remetente: {} → Destinatário: {}",
                item.id,
                item.prioridade,
                item.input.remetenteId(),
                item.input.destinatarioId());

        try {
            EnviarMensagemOutput output = mensagemFacade.enviarMensagem(item.input);

            logger.info("Mensagem processada com sucesso: {} | ID Mensagem: {}",
                    item.id, output.id());

            return output;

        } catch (Exception e) {
            logger.error("Erro ao processar mensagem {}: {}",
                    item.id, e.getMessage(), e);
            throw e;
        }
    }
}

