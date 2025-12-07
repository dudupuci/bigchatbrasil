package io.github.dudupuci.domain.entities;

import io.github.dudupuci.domain.entities.base.Pessoa;
import io.github.dudupuci.domain.enums.Assinatura;
import io.github.dudupuci.domain.enums.TipoDocumento;
import io.github.dudupuci.domain.enums.TipoOperacao;
import io.github.dudupuci.domain.validators.BcbEntityValidator;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
public class Cliente extends Pessoa {
    private String email;
    private String cpfCnpj;
    private TipoDocumento tipoDocumento;
    private Assinatura plano;
    private BigDecimal saldo;
    private BigDecimal limite;
    private String telefone;
    private String sobre;
    private Boolean isAtivo;

    private transient String senha;
    private transient String confirmacaoSenha;

    @Override
    public void validar(TipoOperacao tipoOperacao) {
        if (BcbEntityValidator.isTipoOperacaoCriacao(tipoOperacao)) {
            Objects.requireNonNull(this.nome, "Nome do cliente não pode ser nulo");
            Objects.requireNonNull(this.email, "Email do cliente não pode ser nulo");
            Objects.requireNonNull(this.cpfCnpj, "CPF/CNPJ do cliente não pode ser nulo");
            Objects.requireNonNull(this.tipoDocumento, "Tipo de documento do cliente não pode ser nulo");
            Objects.requireNonNull(this.plano, "Plano do cliente não pode ser nulo");
            Objects.requireNonNull(this.senha, "Senha do cliente não pode ser nulo");
            Objects.requireNonNull(this.confirmacaoSenha, "Confirmacao da senha não pode ser null");

            if (!this.senha.equals(this.confirmacaoSenha)) {
                throw new IllegalArgumentException("A senha e a confirmação de senha não coincidem.");
            }
        } else if (BcbEntityValidator.isTipoOperacaoAtualizacao(tipoOperacao)) {
            Objects.requireNonNull(this.id, "ID do cliente não pode ser nulo para atualização");
        }

    }
}
