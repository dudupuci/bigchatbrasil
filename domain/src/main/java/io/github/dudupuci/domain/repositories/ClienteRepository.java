package io.github.dudupuci.domain.repositories;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.repositories.base.SimpleCrudInterface;

import java.util.Optional;


public interface ClienteRepository extends SimpleCrudInterface<Cliente, Long> {
    Optional<Cliente> buscarPorEmail(String email);
}
