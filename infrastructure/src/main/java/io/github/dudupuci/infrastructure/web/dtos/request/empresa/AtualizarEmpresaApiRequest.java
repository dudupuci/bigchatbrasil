package io.github.dudupuci.infrastructure.web.dtos.request.empresa;

import io.github.dudupuci.application.usecases.empresa.atualizar.AtualizarEmpresaInput;

import java.util.UUID;

public record AtualizarEmpresaApiRequest(
        String razaoSocial,
        String cnpj,
        String telefone,
        String email
) {
    public AtualizarEmpresaInput toApplicationInput(UUID id) {
        return new AtualizarEmpresaInput(
                id,
                this.razaoSocial,
                this.cnpj,
                this.telefone,
                this.email
        );
    }
}

