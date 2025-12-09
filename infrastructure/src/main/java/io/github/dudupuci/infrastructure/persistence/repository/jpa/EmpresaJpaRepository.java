package io.github.dudupuci.infrastructure.persistence.repository.jpa;

import io.github.dudupuci.infrastructure.persistence.EmpresaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmpresaJpaRepository extends JpaRepository<EmpresaJpaEntity, Long> {

    @Query("SELECT e FROM EmpresaJpaEntity e WHERE LOWER(e.email) = LOWER(:email)")
    Optional<EmpresaJpaEntity> findByEmail(@Param("email") String email);
}
