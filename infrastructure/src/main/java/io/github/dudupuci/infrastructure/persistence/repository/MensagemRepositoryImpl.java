package io.github.dudupuci.infrastructure.persistence.repository;

import io.github.dudupuci.domain.entities.Mensagem;
import io.github.dudupuci.domain.repositories.MensagemRepository;
import io.github.dudupuci.infrastructure.persistence.mapper.MensagemMapper;
import io.github.dudupuci.infrastructure.persistence.repository.jpa.MensagemJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class MensagemRepositoryImpl implements MensagemRepository {

    private final MensagemJpaRepository mensagemJpaRepository;
    private final MensagemMapper mensagemMapper;

    public MensagemRepositoryImpl(MensagemJpaRepository mensagemJpaRepository, MensagemMapper mensagemMapper) {
        this.mensagemJpaRepository = mensagemJpaRepository;
        this.mensagemMapper = mensagemMapper;
    }

    @Transactional
    @Override
    public Mensagem salvar(Mensagem mensagem) {
        mensagem.setDataCriacao(Instant.now());
        mensagem.setDataAtualizacao(Instant.now());

        var jpaEntity = mensagemMapper.toJpaEntity(mensagem);
        var savedEntity = mensagemJpaRepository.save(jpaEntity);

        return mensagemMapper.toDomain(savedEntity);
    }

    @Transactional
    @Override
    public void atualizar(Mensagem mensagem) {
        mensagem.setDataAtualizacao(Instant.now());

        var jpaEntity = mensagemMapper.toJpaEntity(mensagem);
        mensagemJpaRepository.save(jpaEntity);
    }

    @Override
    public Optional<Mensagem> buscarPorId(Long id) {
        return mensagemJpaRepository.findById(id)
                .map(mensagemMapper::toDomain);
    }

    @Transactional
    @Override
    public void deletarPorId(Long id) {
        mensagemJpaRepository.deleteById(id);
    }

    @Override
    public List<Mensagem> buscarPorConversaId(UUID conversaId) {
        return mensagemJpaRepository.findByConversaId(conversaId)
                .stream()
                .map(mensagemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Mensagem> buscarPorDestinatarioId(Long destinatarioId) {
        return mensagemJpaRepository.findByDestinatarioId(destinatarioId)
                .stream()
                .map(mensagemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Mensagem> buscarPorRemetenteId(Long remetenteId) {
        return mensagemJpaRepository.findByRemetenteId(remetenteId)
                .stream()
                .map(mensagemMapper::toDomain)
                .collect(Collectors.toList());
    }
}

