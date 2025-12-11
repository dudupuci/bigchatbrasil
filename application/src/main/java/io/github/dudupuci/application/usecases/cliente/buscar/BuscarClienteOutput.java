package io.github.dudupuci.application.usecases.cliente.buscar;


import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.enums.Sexo;

import java.util.UUID;

public record BuscarClienteOutput(
        UUID id,
        String nome,
        String sobrenome,
        String sexo,
        String email,
        String cpfCnpj,
        String telefone,
        String sobre,
        String senha
) {

    public static BuscarClienteOutput fromDomain(Cliente cliente) {
        return new BuscarClienteOutput(
                cliente.getId(),
                cliente.getNome(),
                cliente.getSobrenome(),
                cliente.getSexo().toString(),
                cliente.getEmail(),
                cliente.getCpfCnpj(),
                cliente.getTelefone(),
                cliente.getSobre(),
                cliente.getSenha()
        );
    }


    public Cliente toDomain() {
        Cliente cliente = new Cliente();
        cliente.setId(this.id);
        cliente.setNome(this.nome);
        cliente.setSobrenome(this.sobrenome);
        cliente.setSexo(this.sexo != null ? Sexo.fromDescricao(this.sexo) : null);
        cliente.setEmail(this.email);
        cliente.setCpfCnpj(this.cpfCnpj);
        cliente.setTelefone(this.telefone);
        cliente.setSobre(this.sobre);
        return cliente;
    }
}

