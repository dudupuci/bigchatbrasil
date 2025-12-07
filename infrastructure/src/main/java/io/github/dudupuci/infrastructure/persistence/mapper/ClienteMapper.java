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

        // Campos de Pessoa
        jpaEntity.setNome(cliente.getNome());
        jpaEntity.setSobrenome(cliente.getSobrenome());
        jpaEntity.setSexo(cliente.getSexo());

        // Campos específicos de Cliente
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

        // Data de criação e atualização
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

        // ID e timestamps
        cliente.setId(jpaEntity.getId());
        cliente.setDataCriacao(jpaEntity.getDataCriacao());
        cliente.setDataAtualizacao(jpaEntity.getDataAtualizacao());

        // Campos de Pessoa
        cliente.setNome(jpaEntity.getNome());
        cliente.setSobrenome(jpaEntity.getSobrenome());
        cliente.setSexo(jpaEntity.getSexo());

        // Campos específicos de Cliente
        cliente.setEmail(jpaEntity.getEmail());
        cliente.setCpfCnpj(jpaEntity.getCpfCnpj());
        cliente.setTipoDocumento(jpaEntity.getTipoDocumento());
        cliente.setPlano(jpaEntity.getPlano());
        cliente.setSaldo(jpaEntity.getSaldo());
        cliente.setLimite(jpaEntity.getLimite());
        cliente.setTelefone(jpaEntity.getTelefone());
        cliente.setSobre(jpaEntity.getSobre());
        cliente.setSenha(jpaEntity.getSenha());
        cliente.setIsAtivo(jpaEntity.getIsAtivo());

        return cliente;
    }
}
