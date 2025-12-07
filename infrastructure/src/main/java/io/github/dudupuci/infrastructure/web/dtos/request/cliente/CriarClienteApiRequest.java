package io.github.dudupuci.infrastructure.web.dtos.request.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteInput;

public record CriarClienteApiRequest(
        String nome,
        String sobrenome,
        String sexo,
        String email,
        @JsonProperty(value = "cpf_cnpj")
        String cpfCnpj,
        String senha,
        @JsonProperty(value = "confirmacao_senha")
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
