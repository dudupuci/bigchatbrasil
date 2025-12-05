package io.github.dudupuci.infrastructure.persistence.mapper;

import io.github.dudupuci.domain.entities.Mensagem;
import io.github.dudupuci.infrastructure.persistence.MensagemJpaEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MensagemMapper {

    public MensagemJpaEntity toJpaEntity(Mensagem mensagem) {
        if (mensagem == null) {
            return null;
        }

        MensagemJpaEntity jpaEntity = new MensagemJpaEntity();
        jpaEntity.setConversaId(mensagem.getConversaId());
        jpaEntity.setRemetenteId(mensagem.getRemetenteId());
        jpaEntity.setDestinatarioId(mensagem.getDestinatarioId());
        jpaEntity.setConteudo(mensagem.getConteudo());
        jpaEntity.setTipo(mensagem.getTipo());
        jpaEntity.setStatus(mensagem.getStatus());
        jpaEntity.setPrioridade(mensagem.getPrioridade());
        jpaEntity.setCusto(mensagem.getCusto());

        if (mensagem.getDataCriacao() != null) {
            jpaEntity.setDataCriacao(mensagem.getDataCriacao());
        } else {
            jpaEntity.setDataCriacao(Instant.now());
        }

        if (mensagem.getDataAtualizacao() != null) {
            jpaEntity.setDataAtualizacao(mensagem.getDataAtualizacao());
        } else {
            jpaEntity.setDataAtualizacao(Instant.now());
        }

        return jpaEntity;
    }

    public Mensagem toDomain(MensagemJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        Mensagem mensagem = new Mensagem();
        mensagem.setId(jpaEntity.getId());
        mensagem.setConversaId(jpaEntity.getConversaId());
        mensagem.setRemetenteId(jpaEntity.getRemetenteId());
        mensagem.setDestinatarioId(jpaEntity.getDestinatarioId());
        mensagem.setConteudo(jpaEntity.getConteudo());
        mensagem.setTipo(jpaEntity.getTipo());
        mensagem.setStatus(jpaEntity.getStatus());
        mensagem.setPrioridade(jpaEntity.getPrioridade());
        mensagem.setCusto(jpaEntity.getCusto());
        mensagem.setDataCriacao(jpaEntity.getDataCriacao());
        mensagem.setDataAtualizacao(jpaEntity.getDataAtualizacao());

        return mensagem;
    }
}

