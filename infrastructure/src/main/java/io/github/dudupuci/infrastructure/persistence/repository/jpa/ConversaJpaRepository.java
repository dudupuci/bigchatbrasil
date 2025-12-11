package io.github.dudupuci.infrastructure.persistence.repository.jpa;

import io.github.dudupuci.infrastructure.persistence.ConversaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ConversaJpaRepository extends JpaRepository<ConversaJpaEntity, UUID> {

    @Query("SELECT c FROM ConversaJpaEntity c WHERE c.usuario1Id = :usuarioId OR c.usuario2Id = :usuarioId")
    List<ConversaJpaEntity> findByUsuarioId(@Param("usuarioId") UUID usuarioId);
}

