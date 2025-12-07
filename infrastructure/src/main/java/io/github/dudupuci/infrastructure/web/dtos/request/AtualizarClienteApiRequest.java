package io.github.dudupuci.infrastructure.web.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.dudupuci.application.usecases.cliente.atualizar.AtualizarClienteInput;

public record AtualizarClienteApiRequest(
        String nome,
        String sobrenome,
        String sexo,
        String email,
        @JsonProperty(value = "cpf_cnpj")
        String cpfCnpj,
        @JsonProperty(value = "tipo_documento")
        String tipoDocumento,
        String telefone,
        String sobre
) {
    public AtualizarClienteInput toApplicationInput(Long id) {
        return new AtualizarClienteInput(
                id,
                this.nome,
                this.sobrenome,
                this.sexo,
                this.email,
                this.cpfCnpj,
                this.tipoDocumento,
                this.telefone,
                this.sobre
        );
    }
}

