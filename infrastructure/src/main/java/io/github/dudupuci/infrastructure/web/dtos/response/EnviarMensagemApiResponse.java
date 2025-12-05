package io.github.dudupuci.infrastructure.web.dtos.response;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemOutput;
import io.github.dudupuci.domain.entities.Mensagem;

import java.time.Instant;
import java.util.UUID;

public record EnviarMensagemApiResponse(
        Long id,
        UUID conversaId,
        Long remetenteId,
        Long destinatarioId,
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

