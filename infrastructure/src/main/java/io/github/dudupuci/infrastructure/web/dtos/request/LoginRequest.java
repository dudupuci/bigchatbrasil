package io.github.dudupuci.infrastructure.web.dtos.request;

import io.github.dudupuci.domain.enums.TipoUsuario;

public record LoginRequest(
        String email,
        String senha,
        TipoUsuario tipo
) {
}