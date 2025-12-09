package io.github.dudupuci.infrastructure.web.dtos.request.mensagem;

/**
 * Request para criar uma conversa entre dois usuários
 */
public record CriarConversaApiRequest(
        Long destinatarioId,
        String tipoDestinatario // "CLIENTE" ou "EMPRESA"
) {
}

