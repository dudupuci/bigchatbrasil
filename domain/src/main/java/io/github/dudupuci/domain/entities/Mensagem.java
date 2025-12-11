package io.github.dudupuci.domain.entities;

import io.github.dudupuci.domain.entities.base.Notificacao;
import io.github.dudupuci.domain.enums.TipoOperacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Mensagem extends Notificacao {
    private UUID conversaId;
    private UUID destinatarioId;

    @Override
    public void validar(TipoOperacao tipoOperacao) {

    }
}
