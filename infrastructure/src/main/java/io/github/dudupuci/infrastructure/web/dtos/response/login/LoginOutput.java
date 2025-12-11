package io.github.dudupuci.infrastructure.web.dtos.response.login;

import io.github.dudupuci.domain.enums.TipoUsuario;

import java.util.UUID;

/**
 * DTO com as informações do usuário logado
 */
public record LoginOutput(
        String sessionId,
        UUID id,
        String nome,
        String email,
        TipoUsuario tipo
) {
}

