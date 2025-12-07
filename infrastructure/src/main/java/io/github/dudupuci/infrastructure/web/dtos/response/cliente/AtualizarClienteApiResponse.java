package io.github.dudupuci.infrastructure.web.dtos.response.cliente;

public record AtualizarClienteApiResponse(
        Long id,
        String nome,
        Boolean sucesso
) {
    public static AtualizarClienteApiResponse toApiResponse(Long id, String nome) {
        return new AtualizarClienteApiResponse(
                id,
                nome,
                true
        );
    }
}

