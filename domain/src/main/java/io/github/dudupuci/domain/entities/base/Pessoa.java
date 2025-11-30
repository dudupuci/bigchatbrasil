package io.github.dudupuci.domain.entities.base;

import io.github.dudupuci.domain.enums.Sexo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Pessoa extends Entidade<Long> {
    protected String nome;
    protected Sexo sexo;
}
