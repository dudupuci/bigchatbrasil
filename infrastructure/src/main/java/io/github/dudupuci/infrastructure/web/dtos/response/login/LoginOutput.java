package io.github.dudupuci.infrastructure.web.dtos.response.login;

import io.github.dudupuci.domain.enums.TipoUsuario;

/**
 * DTO com as informações do usuário logado
 */
public record LoginOutput(
        String sessionId,
        Long id,
        String nome,
        String email,
        TipoUsuario tipo
) {
}

