package io.github.dudupuci.infrastructure.persistence.repository;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.repositories.ClienteRepository;
import io.github.dudupuci.infrastructure.persistence.ClienteJpaEntity;
import io.github.dudupuci.infrastructure.persistence.mapper.ClienteMapper;
import io.github.dudupuci.infrastructure.persistence.repository.jpa.ClienteJpaRepository;
import io.github.dudupuci.infrastructure.validation.groups.OnCreate;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteJpaRepository clienteJpaRepository;
    private final ClienteMapper clienteMapper;
    private final Validator validator;

    public ClienteRepositoryImpl(
            ClienteJpaRepository clienteJpaRepository,
            ClienteMapper clienteMapper,
            Validator validator
    ) {
        this.clienteJpaRepository = clienteJpaRepository;
        this.clienteMapper = clienteMapper;
        this.validator = validator;
    }

    @Transactional
    @Override
    public Cliente salvar(Cliente cliente) {
        if (cliente.getDataCriacao() == null) {
            cliente.setDataCriacao(Instant.now());
        }

        cliente.setDataAtualizacao(Instant.now());

        var jpaEntity = clienteMapper.toJpaEntity(cliente);

        // ✅ VALIDAÇÃO com grupo OnCreate (apenas na criação)
        Set<ConstraintViolation<ClienteJpaEntity>> violations = validator.validate(jpaEntity, OnCreate.class);
        if (!violations.isEmpty()) {
            String errors = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Erro de validação: " + errors);
        }

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

        atualizarCamposBasicosCliente(cliente, jpaEntity);
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

    private void atualizarCamposBasicosCliente(Cliente cliente, ClienteJpaEntity jpaEntity) {
        if (cliente.getNome() != null) {
            jpaEntity.setNome(cliente.getNome());
        }
        if (cliente.getSobrenome() != null) {
            jpaEntity.setSobrenome(cliente.getSobrenome());
        }
        if (cliente.getSexo() != null) {
            jpaEntity.setSexo(cliente.getSexo());
        }
        if (cliente.getEmail() != null) {
            jpaEntity.setEmail(cliente.getEmail());
        }
        if (cliente.getCpfCnpj() != null) {
            jpaEntity.setCpfCnpj(cliente.getCpfCnpj());
        }
        if (cliente.getTipoDocumento() != null) {
            jpaEntity.setTipoDocumento(cliente.getTipoDocumento());
        }
        if (cliente.getPlano() != null) {
            jpaEntity.setPlano(cliente.getPlano());
        }
        if (cliente.getSaldo() != null) {
            jpaEntity.setSaldo(cliente.getSaldo());
        }
        if (cliente.getLimite() != null) {
            jpaEntity.setLimite(cliente.getLimite());
        }
        if (cliente.getTelefone() != null) {
            jpaEntity.setTelefone(cliente.getTelefone());
        }
        if (cliente.getSobre() != null) {
            jpaEntity.setSobre(cliente.getSobre());
        }
        if (cliente.getSenha() != null) {
            jpaEntity.setSenha(cliente.getSenha());
        }
        if (cliente.getIsAtivo() != null) {
            jpaEntity.setIsAtivo(cliente.getIsAtivo());
        }
    }

}
