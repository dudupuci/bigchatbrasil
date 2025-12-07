package io.github.dudupuci.infrastructure.web.dtos.response;

public record AuthResponse(
        String sessionId
) {
    public static AuthResponse of(String sessionId) {
        return new AuthResponse(sessionId);
    }
}

