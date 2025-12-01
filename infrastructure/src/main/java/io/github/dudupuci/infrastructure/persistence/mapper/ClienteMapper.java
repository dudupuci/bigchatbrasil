package io.github.dudupuci.infrastructure.persistence.mapper;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.infrastructure.persistence.ClienteJpaEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ClienteMapper {

    public ClienteJpaEntity toJpaEntity(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteJpaEntity jpaEntity = new ClienteJpaEntity();
        // Não seta o ID - é gerado automaticamente pelo banco
        jpaEntity.setNome(cliente.getNome());
        jpaEntity.setSobrenome(cliente.getSobrenome());
        jpaEntity.setSexo(cliente.getSexo());
        jpaEntity.setEmail(cliente.getEmail());
        jpaEntity.setCpf(cliente.getCpf());
        jpaEntity.setTelefone(cliente.getTelefone());
        jpaEntity.setSobre(cliente.getSobre());

        if (cliente.getDataCriacao() != null) {
            jpaEntity.setDataCriacao(cliente.getDataCriacao());
        } else {
            jpaEntity.setDataCriacao(Instant.now());
        }

        if (cliente.getDataAtualizacao() != null) {
            jpaEntity.setDataAtualizacao(cliente.getDataAtualizacao());
        } else {
            jpaEntity.setDataAtualizacao(Instant.now());
        }

        return jpaEntity;
    }

    public Cliente toDomain(ClienteJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setId(jpaEntity.getId());
        cliente.setNome(jpaEntity.getNome());
        cliente.setSexo(jpaEntity.getSexo());
        cliente.setEmail(jpaEntity.getEmail());
        cliente.setCpf(jpaEntity.getCpf());
        cliente.setTelefone(jpaEntity.getTelefone());
        cliente.setSobre(jpaEntity.getSobre());
        cliente.setDataCriacao(jpaEntity.getDataCriacao());
        cliente.setDataAtualizacao(jpaEntity.getDataAtualizacao());

        return cliente;
    }
}
