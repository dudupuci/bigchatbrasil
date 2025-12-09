package io.github.dudupuci.infrastructure.web.dtos.response.mensagem;

import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensOutput;

import java.util.List;
import java.util.UUID;

/**
 * Response da API para listagem de mensagens
 */
public record ListarMensagensApiResponse(
        UUID conversaId,
        List<MensagemApiResponse> mensagens,
        int total
) {
    public static ListarMensagensApiResponse toApiResponse(ListarMensagensOutput output) {
        List<MensagemApiResponse> mensagensApi = output.mensagens().stream()
                .map(MensagemApiResponse::fromDto)
                .toList();

        return new ListarMensagensApiResponse(
                output.conversaId(),
                mensagensApi,
                output.total()
        );
    }
}

