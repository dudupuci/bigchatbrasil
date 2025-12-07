package io.github.dudupuci.infrastructure.web.dtos.request.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.dudupuci.application.usecases.cliente.atualizar.AtualizarClienteInput;

import java.math.BigDecimal;

/**
 * Request para atualização PARCIAL de cliente
 * TODOS os campos são opcionais (nullable)
 * Apenas os campos enviados no JSON serão atualizados
 */
public record AtualizarClienteApiRequest(
        String nome,
        String sobrenome,
        String sexo,
        String email,

        @JsonProperty(value = "cpf_cnpj")
        String cpfCnpj,

        @JsonProperty(value = "tipo_documento")
        String tipoDocumento,

        String plano,
        BigDecimal saldo,
        BigDecimal limite,
        String telefone,
        String sobre,

        @JsonProperty(value = "is_ativo")
        Boolean isAtivo
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
                this.plano,
                this.saldo,
                this.limite,
                this.telefone,
                this.sobre,
                this.isAtivo,
                null,  // senha não pode ser atualizada por este endpoint
                null   // confirmacaoSenha não pode ser atualizada por este endpoint
        );
    }
}

