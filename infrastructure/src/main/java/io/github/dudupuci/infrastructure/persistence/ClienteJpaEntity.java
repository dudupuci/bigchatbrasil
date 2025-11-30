package io.github.dudupuci.infrastructure.persistence;

import io.github.dudupuci.infrastructure.persistence.base.PessoaJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
public class ClienteJpaEntity extends PessoaJpaEntity {
    @Column(nullable = false)
    private String email;
    private String documento;
    @Column(nullable = false)
    private String telefone;
    private String sobre;
}
