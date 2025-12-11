package io.github.dudupuci.application.usecases.mensagem.listarconversas;

import io.github.dudupuci.domain.enums.TipoUsuario;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO com informações de uma conversa
 */
public record ConversaDto(
        UUID conversaId,
        UUID outroUsuarioId,
        String outroUsuarioNome,
        TipoUsuario outroUsuarioTipo,
        String ultimaMensagem,
        Instant ultimaMensagemDataHora,
        int mensagensNaoLidas
) {
}

