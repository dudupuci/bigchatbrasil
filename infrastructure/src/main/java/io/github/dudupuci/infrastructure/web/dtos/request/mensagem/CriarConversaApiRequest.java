package io.github.dudupuci.infrastructure.web.dtos.request.mensagem;

import java.util.UUID;

/**
 * Request para criar uma conversa entre dois usuários
 */
public record CriarConversaApiRequest(
        UUID destinatarioId,
        String tipoDestinatario // "CLIENTE" ou "EMPRESA"
) {
}

