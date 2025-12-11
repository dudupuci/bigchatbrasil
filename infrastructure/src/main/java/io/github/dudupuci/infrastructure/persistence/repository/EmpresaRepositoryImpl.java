package io.github.dudupuci.infrastructure.persistence.repository;

import io.github.dudupuci.domain.entities.Empresa;
import io.github.dudupuci.domain.repositories.EmpresaRepository;
import io.github.dudupuci.infrastructure.persistence.mapper.EmpresaMapper;
import io.github.dudupuci.infrastructure.persistence.repository.jpa.EmpresaJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EmpresaRepositoryImpl implements EmpresaRepository {

    private final EmpresaJpaRepository empresaJpaRepository;
    private final EmpresaMapper empresaMapper;

    public EmpresaRepositoryImpl(EmpresaJpaRepository empresaJpaRepository, EmpresaMapper empresaMapper) {
        this.empresaJpaRepository = empresaJpaRepository;
        this.empresaMapper = empresaMapper;
    }

    @Override
    public Empresa salvar(Empresa empresa) {
        empresa.setDataCriacao(Instant.now());
        empresa.setDataAtualizacao(Instant.now());

        var jpaEntity = empresaMapper.toJpaEntity(empresa);
        var savedEntity = empresaJpaRepository.save(jpaEntity);

        return empresaMapper.toDomain(savedEntity);
    }

    @Override
    public void atualizar(Empresa empresa) {
        empresa.setDataAtualizacao(Instant.now());

        var jpaEntity = empresaMapper.toJpaEntity(empresa);
        empresaJpaRepository.save(jpaEntity);
    }

    @Override
    public Optional<Empresa> buscarPorId(UUID id) {
        return empresaJpaRepository.findById(id)
                .map(empresaMapper::toDomain);
    }

    @Override
    public void deletarPorId(UUID id) {
        empresaJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Empresa> buscarPorEmail(String email) {
        return empresaJpaRepository.findByEmail(email).map(empresaMapper::toDomain);
    }
}

