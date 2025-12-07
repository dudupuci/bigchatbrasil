package io.github.dudupuci.infrastructure.persistence.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Getter
public abstract class JpaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    protected Long id;

    @Setter
    @Column(name = "data_criacao", updatable = false, nullable = false)
    protected Instant dataCriacao;

    @Setter
    @Column(name = "data_atualizacao", nullable = false)
    protected Instant dataAtualizacao;
}
