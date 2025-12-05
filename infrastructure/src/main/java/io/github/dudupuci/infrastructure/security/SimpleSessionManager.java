package io.github.dudupuci.infrastructure.security;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gerenciador simples de sessões - sem JWT
 * Apenas armazena sessionId -> clienteId
 */
@Service
public class SimpleSessionManager {

    private final Map<String, Long> sessions = new ConcurrentHashMap<>();

    /**
     * Cria uma nova sessão para um cliente
     */
    public String createSession(Long clienteId) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, clienteId);
        return sessionId;
    }

    /**
     * Valida uma sessão e retorna o ID do cliente
     */
    public Long getClienteId(String sessionId) {
        return sessions.get(sessionId);
    }

    /**
     * Verifica se uma sessão é válida
     */
    public boolean isValidSession(String sessionId) {
        return sessionId != null && sessions.containsKey(sessionId);
    }

    /**
     * Remove uma sessão (logout)
     */
    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    /**
     * Retorna número de sessões ativas
     */
    public int getActiveSessions() {
        return sessions.size();
    }
}

