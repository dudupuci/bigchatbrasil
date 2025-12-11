package io.github.dudupuci.infrastructure.persistence.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class JpaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    protected UUID id;

    @Setter
    @Column(name = "data_criacao", updatable = false, nullable = false)
    protected Instant dataCriacao;

    @Setter
    @Column(name = "data_atualizacao", nullable = false)
    protected Instant dataAtualizacao;
}
