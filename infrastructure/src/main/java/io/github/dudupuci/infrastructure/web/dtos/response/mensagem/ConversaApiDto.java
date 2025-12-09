package io.github.dudupuci.infrastructure.web.dtos.response.mensagem;

import io.github.dudupuci.application.usecases.mensagem.listarconversas.ConversaDto;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO da API para uma conversa
 */
public record ConversaApiDto(
        String conversaId,
        Long outroUsuarioId,
        String outroUsuarioNome,
        String outroUsuarioTipo,
        String ultimaMensagem,
        Instant ultimaMensagemDataHora,
        int mensagensNaoLidas
) {
    public static ConversaApiDto fromDto(ConversaDto dto) {
        return new ConversaApiDto(
                dto.conversaId().toString(),
                dto.outroUsuarioId(),
                dto.outroUsuarioNome(),
                dto.outroUsuarioTipo().name(),
                dto.ultimaMensagem(),
                dto.ultimaMensagemDataHora(),
                dto.mensagensNaoLidas()
        );
    }
}

