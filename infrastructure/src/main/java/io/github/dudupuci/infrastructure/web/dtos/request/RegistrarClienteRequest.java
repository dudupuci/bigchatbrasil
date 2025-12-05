package io.github.dudupuci.infrastructure.web.dtos.request;

public record RegistrarClienteRequest(
        String nome,
        String sobrenome,
        String sexo,
        String email,
        String senha,
        String documento,
        String telefone,
        String sobre
) {
}

