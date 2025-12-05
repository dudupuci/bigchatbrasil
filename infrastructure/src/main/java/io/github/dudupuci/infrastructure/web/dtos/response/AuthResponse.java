package io.github.dudupuci.infrastructure.web.dtos.response;

public record AuthResponse(
        String sessionId,
        String email,
        Long clienteId
) {
    public static AuthResponse of(String sessionId, String email, Long clienteId) {
        return new AuthResponse(sessionId, email, clienteId);
    }
}

