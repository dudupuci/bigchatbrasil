package io.github.dudupuci.infrastructure.web.dtos.request.login;

import io.github.dudupuci.domain.enums.TipoUsuario;

public record LoginApiRequest(
        String email,
        String senha,
        TipoUsuario tipo
) {
}