package io.github.dudupuci.infrastructure.web.dtos.request;

public record LoginRequest(
        String email,
        String senha
) {
}