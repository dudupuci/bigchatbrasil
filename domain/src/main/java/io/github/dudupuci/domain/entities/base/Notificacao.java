package io.github.dudupuci.domain.entities.base;

import io.github.dudupuci.domain.enums.PrioridadeNotificacao;
import io.github.dudupuci.domain.enums.StatusNotificacao;
import io.github.dudupuci.domain.enums.TipoNotificacao;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public abstract class Notificacao extends Entidade {
    private TipoNotificacao tipo;
    private StatusNotificacao status;
    private PrioridadeNotificacao prioridade;
    private BigDecimal custo;
    private String conteudo;
    private UUID remetenteId;
}
