package io.github.dudupuci.application.usecases.empresa.buscar;

public record BuscarEmpresaOutput(
        Long id,
        String razaoSocial,
        String cnpj,
        String telefone,
        String email
) {
}

