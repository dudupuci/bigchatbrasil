package io.github.dudupuci.application.usecases.cliente.criar;

public record CriarClienteOutput(
        Long id,
        String nome,
        Boolean sucesso
) {
}
