package io.github.dudupuci.infrastructure.persistence.repository;

import io.github.dudupuci.domain.entities.Conversa;
import io.github.dudupuci.domain.repositories.ConversaRepository;
import io.github.dudupuci.infrastructure.persistence.ConversaJpaEntity;
import io.github.dudupuci.infrastructure.persistence.repository.jpa.ConversaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ConversaRepositoryImpl implements ConversaRepository {

    private final ConversaJpaRepository conversaJpaRepository;

    public ConversaRepositoryImpl(ConversaJpaRepository conversaJpaRepository) {
        this.conversaJpaRepository = conversaJpaRepository;
    }

    @Override
    public Conversa salvar(Conversa conversa) {
        ConversaJpaEntity jpaEntity = toJpaEntity(conversa);
        ConversaJpaEntity saved = conversaJpaRepository.save(jpaEntity);
        return toDomain(saved);
    }

    @Override
    public Optional<Conversa> buscarPorId(UUID conversaId) {
        return conversaJpaRepository.findById(conversaId)
                .map(this::toDomain);
    }

    @Override
    public List<Conversa> buscarPorUsuarioId(Long usuarioId) {
        return conversaJpaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorId(UUID conversaId) {
        return conversaJpaRepository.existsById(conversaId);
    }

    private ConversaJpaEntity toJpaEntity(Conversa conversa) {
        ConversaJpaEntity jpa = new ConversaJpaEntity();
        jpa.setConversaId(conversa.getConversaId());
        jpa.setUsuario1Id(conversa.getUsuario1Id());
        jpa.setUsuario1Tipo(conversa.getUsuario1Tipo());
        jpa.setUsuario2Id(conversa.getUsuario2Id());
        jpa.setUsuario2Tipo(conversa.getUsuario2Tipo());
        jpa.setTipo(conversa.getTipo());
        jpa.setCriadaEm(conversa.getCriadaEm());
        jpa.setUltimaAtualizacao(conversa.getUltimaAtualizacao());
        return jpa;
    }

    private Conversa toDomain(ConversaJpaEntity jpa) {
        Conversa conversa = new Conversa();
        conversa.setConversaId(jpa.getConversaId());
        conversa.setUsuario1Id(jpa.getUsuario1Id());
        conversa.setUsuario1Tipo(jpa.getUsuario1Tipo());
        conversa.setUsuario2Id(jpa.getUsuario2Id());
        conversa.setUsuario2Tipo(jpa.getUsuario2Tipo());
        conversa.setTipo(jpa.getTipo());
        conversa.setCriadaEm(jpa.getCriadaEm());
        conversa.setUltimaAtualizacao(jpa.getUltimaAtualizacao());
        return conversa;
    }
}

