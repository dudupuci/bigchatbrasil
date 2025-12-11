package io.github.dudupuci.infrastructure.web.dtos.response.empresa;

import io.github.dudupuci.application.usecases.empresa.buscar.BuscarEmpresaOutput;

import java.util.UUID;

public record BuscarEmpresaApiResponse(
        UUID id,
        String razaoSocial,
        String cnpj,
        String telefone,
        String email
) {
    public static BuscarEmpresaApiResponse toApiResponse(BuscarEmpresaOutput applicationOutput) {
        return new BuscarEmpresaApiResponse(
                applicationOutput.id(),
                applicationOutput.razaoSocial(),
                applicationOutput.cnpj(),
                applicationOutput.telefone(),
                applicationOutput.email()
        );
    }
}

