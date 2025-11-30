package io.github.dudupuci.domain.entities.base;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class Entidade<T> {
    protected T id;
    protected Instant dataCriacao;
    protected Instant dataAtualizacao;

    public abstract void validar();
}
