package io.github.dudupuci.infrastructure.web.dtos.request;

import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaInput;

public record CriarEmpresaApiRequest(
        String razaoSocial,
        String cnpj,
        String telefone,
        String email
) {
    public CriarEmpresaInput toApplicationInput() {
        return new CriarEmpresaInput(
                this.razaoSocial,
                this.cnpj,
                this.telefone,
                this.email
        );
    }
}

