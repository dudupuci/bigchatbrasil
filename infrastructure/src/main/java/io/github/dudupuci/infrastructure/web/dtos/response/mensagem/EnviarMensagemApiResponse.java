package io.github.dudupuci.infrastructure.web.dtos.response.mensagem;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemOutput;

import java.time.Instant;
import java.util.UUID;

public record EnviarMensagemApiResponse(
        UUID id,
        UUID conversaId,
        UUID remetenteId,
        UUID destinatarioId,
        String conteudo,
        String status,
        String prioridade,
        Instant momentoEnvio,
        Instant dataCriacao
) {
    public static EnviarMensagemApiResponse toApiResponse(EnviarMensagemOutput output) {
        return new EnviarMensagemApiResponse(
                output.id(),
                output.conversaId(),
                output.remetenteId(),
                output.destinatarioId(),
                output.conteudo(),
                output.status().name(),
                "",
                output.momentoEnvio(),
                Instant.now()
        );
    }
}

