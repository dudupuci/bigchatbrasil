package io.github.dudupuci.infrastructure.web.dtos.response.mensagem;

import io.github.dudupuci.application.usecases.mensagem.criarconversa.CriarConversaOutput;

/**
 * Response da API para criação de conversa
 */
public record CriarConversaApiResponse(
        String conversaId,
        String message
) {
    public static CriarConversaApiResponse toApiResponse(CriarConversaOutput output) {
        return new CriarConversaApiResponse(
                output.conversaId().toString(),
                "Conversa criada com sucesso!"
        );
    }
}

