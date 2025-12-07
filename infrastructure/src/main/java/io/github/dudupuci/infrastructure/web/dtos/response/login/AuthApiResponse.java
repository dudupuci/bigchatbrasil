package io.github.dudupuci.infrastructure.web.dtos.response.login;

public record AuthApiResponse(
        String sessionId,
        String message
) {
    public static AuthApiResponse of(String sessionId) {
        return new AuthApiResponse(sessionId, "Logado com sucesso!");
    }
}

