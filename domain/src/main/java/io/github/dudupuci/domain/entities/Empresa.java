package io.github.dudupuci.domain.entities;

import io.github.dudupuci.domain.entities.base.Entidade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Empresa extends Entidade<Long> {
    private String razaoSocial;
    private String cnpj;
    private String telefone;
    private String email;

    @Override
    public void validar() {
        Objects.requireNonNull(this.id, "ID da empresa não pode ser nulo");
        Objects.requireNonNull(this.razaoSocial, "Razão social não pode ser nula");
        Objects.requireNonNull(this.cnpj, "CNPJ não pode ser nulo");
    }
}
