package io.github.dudupuci.domain.entities.base;

import io.github.dudupuci.domain.enums.TipoOperacao;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public abstract class Entidade {
    protected UUID id;
    protected Instant dataCriacao;
    protected Instant dataAtualizacao;

    public abstract void validar(TipoOperacao tipoOperacao);
}
