package io.github.dudupuci.domain.entities;

import io.github.dudupuci.domain.entities.base.Pessoa;
import io.github.dudupuci.domain.enums.Assinatura;
import io.github.dudupuci.domain.enums.TipoDocumento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class Cliente extends Pessoa {
    private String email;
    private String cpfCnpj;
    private TipoDocumento tipoDocumento;
    private Assinatura plano;
    private BigDecimal saldo;
    private BigDecimal limite;
    private String telefone;
    private String sobre;
    private Boolean isAtivo;


    @Override
    public void validar() {
    }
}
