package io.github.dudupuci.infrastructure.persistence.repository.jpa;

import io.github.dudupuci.infrastructure.persistence.ClienteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteJpaRepository extends JpaRepository<ClienteJpaEntity, Long> {
        Optional<ClienteJpaEntity> findByEmail(String email);
}

