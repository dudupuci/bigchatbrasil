package io.github.dudupuci.infrastructure.web.dtos.request;

import io.github.dudupuci.application.usecases.cliente.atualizar.AtualizarClienteInput;

public record AtualizarClienteApiRequest(
        String nome,
        String sexo,
        String email,
        String documento,
        String telefone,
        String sobre
) {
    public AtualizarClienteInput toApplicationInput(Long id) {
        return new AtualizarClienteInput(
                id,
                this.nome,
                this.sexo,
                this.email,
                this.documento,
                this.telefone,
                this.sobre
        );
    }
}

