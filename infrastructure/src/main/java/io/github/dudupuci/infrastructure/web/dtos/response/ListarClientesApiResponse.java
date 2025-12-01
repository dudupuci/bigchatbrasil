package io.github.dudupuci.infrastructure.web.dtos.response;

import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClienteOutput;

public record ListarClientesApiResponse(
        Long id,
        String nome,
        String email,
        String documento,
        String telefone
) {
    public static ListarClientesApiResponse toApiResponse(BuscarClienteOutput applicationOutput) {
        return new ListarClientesApiResponse(
                applicationOutput.id(),
                applicationOutput.nome(),
                applicationOutput.email(),
                applicationOutput.documento(),
                applicationOutput.telefone()
        );
    }
}

