package io.github.dudupuci.application.usecases.mensagem.listar;

import io.github.dudupuci.domain.enums.StatusNotificacao;

import java.time.Instant;
import java.util.UUID;

/**
 * Output de uma mensagem individual na listagem
 */
public record MensagemDto(
        Long id,
        UUID conversaId,
        Long remetenteId,
        Long destinatarioId,
        String conteudo,
        StatusNotificacao status,
        Instant momentoEnvio
) {
}

