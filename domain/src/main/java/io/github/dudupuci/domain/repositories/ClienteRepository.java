package io.github.dudupuci.domain.repositories;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.repositories.base.SimpleCrudInterface;

import java.util.Optional;
import java.util.UUID;


public interface ClienteRepository extends SimpleCrudInterface<Cliente, UUID> {
    Optional<Cliente> buscarPorEmail(String email);
}
