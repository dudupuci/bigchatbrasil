package io.github.dudupuci.infrastructure.web.dtos.request;

public record RegistrarEmpresaRequest(
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

