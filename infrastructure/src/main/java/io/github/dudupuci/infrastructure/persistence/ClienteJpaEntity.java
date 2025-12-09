package io.github.dudupuci.infrastructure.persistence;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.enums.Assinatura;
import io.github.dudupuci.domain.enums.TipoDocumento;
import io.github.dudupuci.infrastructure.validation.annotations.CpfOuCnpj;
import io.github.dudupuci.infrastructure.persistence.base.PessoaJpaEntity;
import io.github.dudupuci.infrastructure.validation.groups.OnCreate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
    @Email(message = "Email inválido", groups = OnCreate.class)
    private String email;

    @CpfOuCnpj(groups = OnCreate.class)
    @Column(unique = true)
    private String cpfCnpj;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Enumerated(EnumType.STRING)
    private Assinatura plano;

    private BigDecimal saldo;

    private BigDecimal limite;

    private String telefone;

    private String sobre;

    private String senha;

    @Column(name = "is_ativo")
    private Boolean isAtivo = true;

    public Cliente toDomain() {
        return new Cliente();
    }
}
