package io.github.dudupuci.application.usecases.mensagem.enviar;

import io.github.dudupuci.domain.enums.StatusNotificacao;

import java.time.Instant;
import java.util.UUID;

public record EnviarMensagemOutput(
        Long id,
        UUID conversaId,
        Long remetenteId,
        Long destinatarioId,
        String conteudo,
        StatusNotificacao status,
        Instant momentoEnvio,
        Boolean sucesso
) {
}

