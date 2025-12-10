package io.github.dudupuci.infrastructure.persistence;

import io.github.dudupuci.infrastructure.persistence.base.JpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@NoArgsConstructor
public class EmpresaJpaEntity extends JpaEntity {
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Column(nullable = false, unique = true)
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, unique = true)
    @Email(message = "Email inválido")
    private String email;

    private String senha;
}

