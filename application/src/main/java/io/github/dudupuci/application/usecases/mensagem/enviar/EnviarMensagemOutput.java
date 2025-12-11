package io.github.dudupuci.application.usecases.mensagem.enviar;

import io.github.dudupuci.domain.enums.StatusNotificacao;

import java.time.Instant;
import java.util.UUID;

public record EnviarMensagemOutput(
        UUID id,
        Instant momentoEnvio,
        UUID conversaId,
        UUID remetenteId,
        UUID destinatarioId,
        String conteudo,
        StatusNotificacao status,
        Boolean sucesso
) {
}

