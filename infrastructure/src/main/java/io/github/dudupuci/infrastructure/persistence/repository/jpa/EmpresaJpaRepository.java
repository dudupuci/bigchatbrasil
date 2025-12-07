package io.github.dudupuci.infrastructure.persistence.repository.jpa;

import io.github.dudupuci.infrastructure.persistence.EmpresaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaJpaRepository extends JpaRepository<EmpresaJpaEntity, Long> {
    Optional<EmpresaJpaEntity> findByEmail(String email);
}
