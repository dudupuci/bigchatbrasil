package io.github.dudupuci.infrastructure.persistence;

import io.github.dudupuci.domain.enums.Assinatura;
import io.github.dudupuci.domain.enums.TipoDocumento;
import io.github.dudupuci.infrastructure.configuration.annotations.CpfOuCnpj;
import io.github.dudupuci.infrastructure.persistence.base.PessoaJpaEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
public class ClienteJpaEntity extends PessoaJpaEntity {

    @Column(nullable = false, unique = true)
    @Email(message = "Email inválido")
    private String email;

    @CpfOuCnpj
    @Column(unique = true)
    private String cpfCnpj;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Enumerated(EnumType.STRING)
    private Assinatura plano;

    private BigDecimal saldo;

    private BigDecimal limite;

    @Column(nullable = false)
    private String telefone;

    @NotBlank(message = "O campo 'sobre' não pode estar em branco")
    private String sobre;
}
