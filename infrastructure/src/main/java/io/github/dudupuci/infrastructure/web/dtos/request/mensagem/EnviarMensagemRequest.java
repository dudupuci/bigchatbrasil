package io.github.dudupuci.infrastructure.web.dtos.request.mensagem;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemInput;

import java.util.UUID;

/**
 * Request para enviar mensagem
 * conversaId é OPCIONAL - se não enviado, será gerado automaticamente
 */
public record EnviarMensagemRequest(
        UUID conversaId,
        Long destinatarioId,
        String conteudo,
        String tipo,
        String prioridade
) {

    public EnviarMensagemInput toApplicationInput(Long remetenteId) {
        return new EnviarMensagemInput(
                this.conversaId,
                remetenteId,
                this.destinatarioId,
                this.conteudo,
                this.tipo,
                this.prioridade
        );
    }
}

