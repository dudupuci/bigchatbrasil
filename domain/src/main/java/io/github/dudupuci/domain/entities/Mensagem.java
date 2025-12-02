package io.github.dudupuci.domain.entities;

import io.github.dudupuci.domain.entities.base.Notificacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Mensagem extends Notificacao {
    private UUID conversaId;
    private Long destinatarioId;
}
