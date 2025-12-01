package io.github.dudupuci.infrastructure.web.dtos.response;

import io.github.dudupuci.application.usecases.empresa.buscar.BuscarEmpresaOutput;

public record ListarEmpresasApiResponse(
        Long id,
        String razaoSocial,
        String cnpj,
        String telefone,
        String email
) {
    public static ListarEmpresasApiResponse toApiResponse(BuscarEmpresaOutput applicationOutput) {
        return new ListarEmpresasApiResponse(
                applicationOutput.id(),
                applicationOutput.razaoSocial(),
                applicationOutput.cnpj(),
                applicationOutput.telefone(),
                applicationOutput.email()
        );
    }
}

