package io.github.dudupuci.infrastructure.web.dtos.response.cliente;

import java.util.UUID;

public record AtualizarClienteApiResponse(
        UUID id,
        String nome,
        Boolean sucesso
) {
    public static AtualizarClienteApiResponse toApiResponse(UUID id, String nome) {
        return new AtualizarClienteApiResponse(
                id,
                nome,
                true
        );
    }
}

