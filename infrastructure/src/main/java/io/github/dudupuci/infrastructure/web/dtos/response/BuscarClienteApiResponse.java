package io.github.dudupuci.infrastructure.web.dtos.response;

import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClienteOutput;

public record BuscarClienteApiResponse(
        String nome,
        String sexo,
        String email,
        String documento,
        String telefone,
        String sobre
) {
    public static BuscarClienteApiResponse toApiResponse(BuscarClienteOutput applicationOutput) {
        return new BuscarClienteApiResponse(
                applicationOutput.nome(),
                applicationOutput.sexo(),
                applicationOutput.email(),
                applicationOutput.cpfCnpj(),
                applicationOutput.telefone(),
                applicationOutput.sobre()
        );
    }
}

