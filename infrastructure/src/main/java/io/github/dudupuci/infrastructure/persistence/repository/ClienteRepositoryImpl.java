package io.github.dudupuci.infrastructure.persistence.repository;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.repositories.ClienteRepository;
import io.github.dudupuci.infrastructure.persistence.mapper.ClienteMapper;
import io.github.dudupuci.infrastructure.persistence.repository.jpa.ClienteJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteJpaRepository clienteJpaRepository;
    private final ClienteMapper clienteMapper;

    public ClienteRepositoryImpl(ClienteJpaRepository clienteJpaRepository, ClienteMapper clienteMapper) {
        this.clienteJpaRepository = clienteJpaRepository;
        this.clienteMapper = clienteMapper;
    }

    @Transactional
    @Override
    public Cliente salvar(Cliente cliente) {
        cliente.setDataCriacao(Instant.now());
        cliente.setDataAtualizacao(Instant.now());

        var jpaEntity = clienteMapper.toJpaEntity(cliente);
        var savedEntity = clienteJpaRepository.save(jpaEntity);

        return clienteMapper.toDomain(savedEntity);
    }

    @Override
    public void atualizar(Cliente cliente) {
        cliente.setDataAtualizacao(Instant.now());

        var jpaEntity = clienteMapper.toJpaEntity(cliente);
        clienteJpaRepository.save(jpaEntity);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteJpaRepository.findById(id)
                .map(clienteMapper::toDomain);
    }

    @Override
    public void deletarPorId(Long id) {
        clienteJpaRepository.deleteById(id);
    }
}
