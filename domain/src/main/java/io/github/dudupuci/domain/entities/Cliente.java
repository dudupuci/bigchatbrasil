package io.github.dudupuci.domain.entities;

import io.github.dudupuci.domain.entities.base.Pessoa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Cliente extends Pessoa {
    private String email;
    private String documento;
    private String telefone;
    private String sobre;

    @Override
    public void validar() {
        Objects.requireNonNull(this.id, "ID do cliente não pode ser nulo");
    }
}
