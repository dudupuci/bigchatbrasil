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
        if (cliente.getDataCriacao() == null) {
            cliente.setDataCriacao(Instant.now());
        }

        cliente.setDataAtualizacao(Instant.now());

        var jpaEntity = clienteMapper.toJpaEntity(cliente);
        var savedEntity = clienteJpaRepository.save(jpaEntity);

        return clienteMapper.toDomain(savedEntity);
    }

    @Transactional
    @Override
    public void atualizar(Cliente cliente) {
        if (cliente.getId() == null) {
            throw new IllegalArgumentException("Cliente deve ter um ID para ser atualizado");
        }

        var jpaEntity = clienteJpaRepository.findById(cliente.getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado para atualização"));

        // Atualiza apenas os campos (mantém o ID original)
        jpaEntity.setNome(cliente.getNome());
        jpaEntity.setSobrenome(cliente.getSobrenome());
        jpaEntity.setSexo(cliente.getSexo());
        jpaEntity.setEmail(cliente.getEmail());
        jpaEntity.setCpfCnpj(cliente.getCpfCnpj());
        jpaEntity.setTipoDocumento(cliente.getTipoDocumento());
        jpaEntity.setPlano(cliente.getPlano());
        jpaEntity.setSaldo(cliente.getSaldo());
        jpaEntity.setLimite(cliente.getLimite());
        jpaEntity.setTelefone(cliente.getTelefone());
        jpaEntity.setSobre(cliente.getSobre());
        jpaEntity.setSenha(cliente.getSenha());
        jpaEntity.setIsAtivo(cliente.getIsAtivo());
        jpaEntity.setDataAtualizacao(Instant.now());

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

    @Override
    public Optional<Cliente> buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        return clienteJpaRepository.findByEmail(email)
                .map(clienteMapper::toDomain);
    }
}
