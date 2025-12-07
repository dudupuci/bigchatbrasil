package io.github.dudupuci.infrastructure.web.dtos.request;

import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteInput;
import io.github.dudupuci.domain.enums.Assinatura;

public record CriarClienteApiRequest(
        String nome,
        String sobrenome,
        String sexo,
        String email,
        String cpfCnpj,
        String senha,
        String confirmacaoSenha,
        String telefone,
        String sobre
) {
    public CriarClienteInput toApplicationInput() {
        return new CriarClienteInput(
                this.nome,
                this.sobrenome,
                this.sexo,
                this.email,
                this.cpfCnpj,
                this.senha,
                this.confirmacaoSenha,
                this.telefone,
                this.sobre
        );
    }
}
