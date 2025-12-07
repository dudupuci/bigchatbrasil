package io.github.dudupuci.infrastructure.security;

import io.github.dudupuci.domain.enums.TipoUsuario;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gerenciador simples de sessões - sem JWT
 * Armazena sessionId -> SessionInfo (com idUsuario e tipoUsuario)
 */
@Service
public class SimpleSessionManager {

    public static final Map<String, SessionInfo> SESSIONS = new ConcurrentHashMap<>();

    /**
     * Cria uma nova sessão para um usuário (cliente ou empresa)
     */
    public String createSession(Long idUsuario, TipoUsuario tipoUsuario) {
        String sessionId = UUID.randomUUID().toString();
        SESSIONS.put(sessionId, new SessionInfo(idUsuario, tipoUsuario));
        return sessionId;
    }

    /**
     * Valida uma sessão e retorna as informações completas (ID + tipo)
     */
    public SessionInfo getSessionInfo(String sessionId) {
        return SESSIONS.get(sessionId);
    }

    /**
     * Retorna apenas o ID do usuário (compatibilidade)
     */
    public Long getIdUsuario(String sessionId) {
        SessionInfo info = SESSIONS.get(sessionId);
        return info != null ? info.idUsuario() : null;
    }

    /**
     * Retorna o tipo do usuário (CLIENTE ou EMPRESA)
     */
    public TipoUsuario getTipoUsuario(String sessionId) {
        SessionInfo info = SESSIONS.get(sessionId);
        return info != null ? info.tipoUsuario() : null;
    }

    /**
     * Verifica se uma sessão é válida
     */
    public boolean isValidSession(String sessionId) {
        return sessionId != null && SESSIONS.containsKey(sessionId);
    }

    /**
     * Verifica se a sessão é de um cliente
     */
    public boolean isCliente(String sessionId) {
        SessionInfo info = SESSIONS.get(sessionId);
        return info != null && info.isCliente();
    }

    /**
     * Verifica se a sessão é de uma empresa
     */
    public boolean isEmpresa(String sessionId) {
        SessionInfo info = SESSIONS.get(sessionId);
        return info != null && info.isEmpresa();
    }

    /**
     * Remove uma sessão (logout)
     */
    public void removeSession(String sessionId) {
        SESSIONS.remove(sessionId);
    }

    /**
     * Retorna número de sessões ativas
     */
    public int getActiveSessions() {
        return SESSIONS.size();
    }
}

