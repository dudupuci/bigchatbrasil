package io.github.dudupuci.domain.entities;

import io.github.dudupuci.domain.entities.base.Pessoa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Cliente extends Pessoa {
    private String email;
    private String cpf;
    private String telefone;
    private String sobre;

    @Override
    public void validar() {
    }
}
