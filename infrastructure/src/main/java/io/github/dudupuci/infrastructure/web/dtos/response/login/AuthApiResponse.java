package io.github.dudupuci.infrastructure.web.dtos.response.login;

import io.github.dudupuci.domain.enums.TipoUsuario;

import java.util.UUID;

public record AuthApiResponse(
        String sessionId,
        UUID id,
        String nome,
        String email,
        TipoUsuario tipo,
        String message
) {
    public static AuthApiResponse of(String sessionId, UUID id, String nome, String email, TipoUsuario tipo) {
        return new AuthApiResponse(sessionId, id, nome, email, tipo, "Logado com sucesso!");
    }
}

