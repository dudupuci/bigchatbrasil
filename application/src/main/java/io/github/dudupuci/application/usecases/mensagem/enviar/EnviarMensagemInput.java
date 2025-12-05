package io.github.dudupuci.application.usecases.mensagem.enviar;

import io.github.dudupuci.domain.entities.Mensagem;
import io.github.dudupuci.domain.enums.PrioridadeNotificacao;
import io.github.dudupuci.domain.enums.StatusNotificacao;
import io.github.dudupuci.domain.enums.TipoNotificacao;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record EnviarMensagemInput(
        UUID conversaId,
        Long remetenteId,
        Long destinatarioId,
        String conteudo,
        String tipo,
        String prioridade
) {

    public static Mensagem criarEntidade(EnviarMensagemInput input) {
        final var mensagem = new Mensagem();
        mensagem.setConversaId(input.conversaId());
        mensagem.setRemetenteId(input.remetenteId());
        mensagem.setDestinatarioId(input.destinatarioId());
        mensagem.setConteudo(input.conteudo());
        mensagem.setTipo(TipoNotificacao.valueOf(input.tipo()));
        mensagem.setPrioridade(PrioridadeNotificacao.valueOf(input.prioridade()));
        mensagem.setStatus(StatusNotificacao.PENDENTE);
        mensagem.setMomentoEnvio(Instant.now());
        mensagem.setCusto(BigDecimal.ZERO);
        return mensagem;
    }
}

