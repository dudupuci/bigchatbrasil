package io.github.dudupuci.infrastructure.web.dtos.request.empresa;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaInput;

public record CriarEmpresaApiRequest(
        @JsonProperty(value = "razao_social")
        String razaoSocial,
        String cnpj,
        String telefone,
        String email,
        String senha,
        @JsonProperty(value = "confirmacao_senha")
        String confirmacaoSenha
) {
    public CriarEmpresaInput toApplicationInput() {
        return new CriarEmpresaInput(
                this.razaoSocial,
                this.cnpj,
                this.telefone,
                this.email,
                this.senha,
                this.confirmacaoSenha
        );
    }
}

