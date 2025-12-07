package io.github.dudupuci.infrastructure.web.dtos.response.cliente;

import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteOutput;

public record CriarClienteApiResponse(
        String id,
        String nome,
        Boolean sucesso
) {
    public static CriarClienteApiResponse toApiResponse(CriarClienteOutput applicationOutput) {
        return new CriarClienteApiResponse(
                applicationOutput.id().toString(),
                applicationOutput.nome(),
                true
        );
    }
}
