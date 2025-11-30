package io.github.dudupuci.infrastructure.persistence.repository;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.repositories.ClienteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {
    @Override
    public Cliente salvar(Cliente cliente) {
        return null;
    }

    @Override
    public void atualizar(Cliente cliente) {

    }

    @Override
    public Optional<Cliente> buscarPorId(Long aLong) {
        return Optional.empty();
    }

    @Override
    public void deletarPorId(Long aLong) {

    }
}
