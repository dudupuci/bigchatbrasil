package io.github.dudupuci.infrastructure.web.dtos.response.mensagem;

import io.github.dudupuci.application.usecases.mensagem.listar.MensagemDto;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO da API para uma mensagem individual
 */
public record MensagemApiResponse(
        UUID id,
        UUID conversaId,
        UUID remetenteId,
        UUID destinatarioId,
        String conteudo,
        String status,
        Instant momentoEnvio
) {
    public static MensagemApiResponse fromDto(MensagemDto dto) {
        return new MensagemApiResponse(
                dto.id(),
                dto.conversaId(),
                dto.remetenteId(),
                dto.destinatarioId(),
                dto.conteudo(),
                dto.status().name(),
                dto.momentoEnvio()
        );
    }
}

