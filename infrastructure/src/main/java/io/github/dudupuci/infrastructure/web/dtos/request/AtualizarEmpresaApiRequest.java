package io.github.dudupuci.infrastructure.web.dtos.request;

import io.github.dudupuci.application.usecases.empresa.atualizar.AtualizarEmpresaInput;

public record AtualizarEmpresaApiRequest(
        String razaoSocial,
        String cnpj,
        String telefone,
        String email
) {
    public AtualizarEmpresaInput toApplicationInput(Long id) {
        return new AtualizarEmpresaInput(
                id,
                this.razaoSocial,
                this.cnpj,
                this.telefone,
                this.email
        );
    }
}

