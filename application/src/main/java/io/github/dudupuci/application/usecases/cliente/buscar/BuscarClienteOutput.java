package io.github.dudupuci.application.usecases.cliente.buscar;

import io.github.dudupuci.domain.enums.Sexo;

public record BuscarClienteOutput(
        Long id,
        String nome,
        Sexo sexo,
        String email,
        String documento,
        String telefone,
        String sobre
) {
}

