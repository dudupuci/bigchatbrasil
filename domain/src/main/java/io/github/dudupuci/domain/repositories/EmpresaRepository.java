package io.github.dudupuci.domain.repositories;

import io.github.dudupuci.domain.entities.Empresa;
import io.github.dudupuci.domain.repositories.base.SimpleCrudInterface;

import java.util.Optional;


public interface EmpresaRepository extends SimpleCrudInterface<Empresa, Long> {
    Optional<Empresa> buscarPorEmail(String email);
}
