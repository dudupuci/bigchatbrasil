package io.github.dudupuci.domain.repositories;

import io.github.dudupuci.domain.entities.Empresa;
import io.github.dudupuci.domain.repositories.base.SimpleCrudInterface;

import java.util.Optional;
import java.util.UUID;


public interface EmpresaRepository extends SimpleCrudInterface<Empresa, UUID> {
    Optional<Empresa> buscarPorEmail(String email);
}
