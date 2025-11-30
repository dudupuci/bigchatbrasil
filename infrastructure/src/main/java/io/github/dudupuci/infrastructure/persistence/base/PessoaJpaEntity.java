package io.github.dudupuci.infrastructure.persistence.base;

import io.github.dudupuci.domain.enums.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class PessoaJpaEntity extends JpaEntity{
    @Column(nullable = false)
    protected String nome;
    @Enumerated(EnumType.STRING)
    protected Sexo sexo;
}
