package io.github.dudupuci.application.usecases.mensagem.listar;

import io.github.dudupuci.domain.enums.StatusNotificacao;

import java.time.Instant;
import java.util.UUID;

/**
 * Output de uma mensagem individual na listagem
 */
public record MensagemDto(
        UUID id,
        UUID conversaId,
        UUID remetenteId,
        UUID destinatarioId,
        String conteudo,
        StatusNotificacao status,
        Instant momentoEnvio
) {
}

