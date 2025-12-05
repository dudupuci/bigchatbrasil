package io.github.dudupuci.infrastructure.persistence.repository.jpa;

import io.github.dudupuci.infrastructure.persistence.MensagemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MensagemJpaRepository extends JpaRepository<MensagemJpaEntity, Long> {
    List<MensagemJpaEntity> findByConversaId(UUID conversaId);
    List<MensagemJpaEntity> findByDestinatarioId(Long destinatarioId);
    List<MensagemJpaEntity> findByRemetenteId(Long remetenteId);
}

