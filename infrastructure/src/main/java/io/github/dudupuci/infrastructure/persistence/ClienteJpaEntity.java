package io.github.dudupuci.infrastructure.persistence;

import io.github.dudupuci.infrastructure.persistence.base.PessoaJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
public class ClienteJpaEntity extends PessoaJpaEntity {

    @Column(nullable = false, unique = true)
    @Email(message = "Email inválido")
    private String email;

    @CPF(message = "CPF inválido")
    @Column(unique = true)
    private String cpf;

    @Column(nullable = false)
    private String telefone;

    @NotBlank(message = "O campo 'sobre' não pode estar em branco")
    private String sobre;
}
