package io.github.dudupuci.infrastructure.web.dtos.response.login;

public record AuthApiResponse(
        String sessionId
) {
    public static AuthApiResponse of(String sessionId) {
        return new AuthApiResponse(sessionId);
    }
}

