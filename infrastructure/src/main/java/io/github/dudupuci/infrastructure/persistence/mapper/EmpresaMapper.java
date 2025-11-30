package io.github.dudupuci.infrastructure.persistence.mapper;

import io.github.dudupuci.domain.entities.Empresa;
import io.github.dudupuci.infrastructure.persistence.EmpresaJpaEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class EmpresaMapper {

    public EmpresaJpaEntity toJpaEntity(Empresa empresa) {
        if (empresa == null) {
            return null;
        }

        EmpresaJpaEntity jpaEntity = new EmpresaJpaEntity();
        jpaEntity.setRazaoSocial(empresa.getRazaoSocial());
        jpaEntity.setCnpj(empresa.getCnpj());
        jpaEntity.setTelefone(empresa.getTelefone());
        jpaEntity.setEmail(empresa.getEmail());

        if (empresa.getDataCriacao() != null) {
            jpaEntity.setDataCriacao(empresa.getDataCriacao());
        } else {
            jpaEntity.setDataCriacao(Instant.now());
        }

        if (empresa.getDataAtualizacao() != null) {
            jpaEntity.setDataAtualizacao(empresa.getDataAtualizacao());
        } else {
            jpaEntity.setDataAtualizacao(Instant.now());
        }

        return jpaEntity;
    }

    public Empresa toDomain(EmpresaJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        Empresa empresa = new Empresa();
        empresa.setId(jpaEntity.getId());
        empresa.setRazaoSocial(jpaEntity.getRazaoSocial());
        empresa.setCnpj(jpaEntity.getCnpj());
        empresa.setTelefone(jpaEntity.getTelefone());
        empresa.setEmail(jpaEntity.getEmail());
        empresa.setDataCriacao(jpaEntity.getDataCriacao());
        empresa.setDataAtualizacao(jpaEntity.getDataAtualizacao());

        return empresa;
    }
}