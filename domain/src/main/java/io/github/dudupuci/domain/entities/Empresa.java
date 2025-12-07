package io.github.dudupuci.domain.entities;

import io.github.dudupuci.domain.entities.base.Entidade;
import io.github.dudupuci.domain.enums.TipoOperacao;
import io.github.dudupuci.domain.validators.BcbEntityValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Empresa extends Entidade {
    private String razaoSocial;
    private String cnpj;
    private String telefone;
    private String email;

    private transient String senha;
    private transient String confirmacaoSenha;

    @Override
    public void validar(TipoOperacao tipoOperacao) {
        if (BcbEntityValidator.isTipoOperacaoCriacao(tipoOperacao)) {
            Objects.requireNonNull(this.razaoSocial, "Razão social não pode ser nula");
            Objects.requireNonNull(this.cnpj, "CNPJ não pode ser nulo");
            Objects.requireNonNull(this.telefone, "Telefone não pode ser nulo");
            Objects.requireNonNull(this.email, "Email não pode ser nulo");
            Objects.requireNonNull(this.senha, "Senha não pode ser nula");
            Objects.requireNonNull(this.confirmacaoSenha, "Confirmação de senha não pode ser nula");

            if (!this.senha.equals(this.confirmacaoSenha)) {
                throw new IllegalArgumentException("A senha e a confirmação de senha não coincidem.");
            }
        } else if (BcbEntityValidator.isTipoOperacaoAtualizacao(tipoOperacao)) {
            Objects.requireNonNull(this.id, "ID da empresa não pode ser nulo para atualização");
        }

    }
}
