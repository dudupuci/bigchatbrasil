package io.github.dudupuci.infrastructure.web.dtos.response.mensagem;

import io.github.dudupuci.application.usecases.mensagem.listarconversas.ConversaDto;
import io.github.dudupuci.application.usecases.mensagem.listarconversas.ListarConversasOutput;

import java.util.List;

/**
 * Response da API para listagem de conversas
 */
public record ListarConversasApiResponse(
        List<ConversaApiDto> conversas,
        int total
) {
    public static ListarConversasApiResponse toApiResponse(ListarConversasOutput output) {
        List<ConversaApiDto> conversasApi = output.conversas().stream()
                .map(ConversaApiDto::fromDto)
                .toList();

        return new ListarConversasApiResponse(conversasApi, output.total());
    }
}

