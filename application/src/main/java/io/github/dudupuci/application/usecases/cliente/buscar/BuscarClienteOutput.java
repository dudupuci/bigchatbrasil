package io.github.dudupuci.application.usecases.cliente.buscar;


public record BuscarClienteOutput(
        Long id,
        String nome,
        String sexo,
        String email,
        String documento,
        String telefone,
        String sobre,
        String senha
) {
}

