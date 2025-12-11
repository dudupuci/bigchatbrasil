package io.github.dudupuci.application.usecases.cliente.criar;

import java.util.UUID;

public record CriarClienteOutput(
        UUID id,
        String nome,
        Boolean sucesso
) {
}
