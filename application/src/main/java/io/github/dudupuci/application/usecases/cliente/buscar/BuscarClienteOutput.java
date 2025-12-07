package io.github.dudupuci.application.usecases.cliente.buscar;


import io.github.dudupuci.domain.entities.Cliente;

public record BuscarClienteOutput(
        Long id,
        String nome,
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
                cliente.getSexo().toString(),
                cliente.getEmail(),
                cliente.getCpfCnpj(),
                cliente.getTelefone(),
                cliente.getSobre(),
                null
        );
    }

    public Cliente toDomain() {
        return new Cliente();
    }
}

