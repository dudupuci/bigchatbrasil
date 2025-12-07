package io.github.dudupuci.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor que valida o sessionId em todas as requisições com @RequiresAuth
 * Adiciona informações da sessão nos atributos da request
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    private final SimpleSessionManager sessionManager;

    public SessionInterceptor(SimpleSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Verifica se o handler é um método de controller
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        // Verifica se o método ou a classe tem @RequiresAuth
        boolean requiresAuth = handlerMethod.hasMethodAnnotation(RequiresAuth.class) ||
                               handlerMethod.getBeanType().isAnnotationPresent(RequiresAuth.class);

        // Se não requer autenticação, deixa passar
        if (!requiresAuth) {
            return true;
        }

        // Pega o sessionId do header
        String sessionId = request.getHeader("X-Session-Id");

        // Se não tem sessionId, bloqueia
        if (sessionId == null || sessionId.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Autenticação necessária. Forneça X-Session-Id no header.\"}");
            response.setContentType("application/json");
            return false;
        }

        // Valida a sessão
        if (!sessionManager.isValidSession(sessionId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Sessão inválida ou expirada\"}");
            response.setContentType("application/json");
            return false;
        }

        // Adiciona informações da sessão na request para uso nos controllers
        SessionInfo sessionInfo = sessionManager.getSessionInfo(sessionId);
        request.setAttribute("sessionInfo", sessionInfo);
        request.setAttribute("userId", sessionInfo.idUsuario());
        request.setAttribute("userType", sessionInfo.tipoUsuario());

        return true;
    }
}

