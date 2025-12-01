package io.github.dudupuci.infrastructure.web.dtos.request;

import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteInput;

public record CriarClienteApiRequest(
        String nome,
        String sobrenome,
        String sexo,
        String email,
        String documento,
        String telefone,
        String sobre
) {
    public CriarClienteInput toApplicationInput() {
        return new CriarClienteInput(
                this.nome,
                this.sobrenome,
                this.sexo,
                this.email,
                this.documento,
                this.telefone,
                this.sobre
        );
    }
}
