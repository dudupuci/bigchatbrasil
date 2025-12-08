package io.github.dudupuci.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor que valida o sessionId em todas as requisições com @RequiresAuth
 * Adiciona informações da sessão nos atributos da request
 **/
@Component
public class SessionInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    private final SimpleSessionManager sessionManager;

    public SessionInterceptor(SimpleSessionManager sessionManager) {
        this.sessionManager = sessionManager;
        logger.info("🔧 SessionInterceptor CRIADO pelo Spring! SessionManager injetado: {}", sessionManager != null);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        // ✅ LOG SEMPRE (INFO) - Para garantir que está sendo executado
        logger.info("🔍 SessionInterceptor EXECUTADO: {} {}", method, requestUri);

        // Verifica se o handler é um método de controller
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            logger.info("   ↳ Não é HandlerMethod ({}), liberando", handler.getClass().getSimpleName());
            return true;
        }

        // Verifica se o método ou a classe tem @RequiresAuth
        boolean requiresAuth = handlerMethod.hasMethodAnnotation(RequiresAuth.class) ||
                               handlerMethod.getBeanType().isAnnotationPresent(RequiresAuth.class);

        logger.info("   ↳ Controller: {}", handlerMethod.getBeanType().getSimpleName());
        logger.info("   ↳ Método: {}", handlerMethod.getMethod().getName());
        logger.info("   ↳ @RequiresAuth na classe? {}", handlerMethod.getBeanType().isAnnotationPresent(RequiresAuth.class));
        logger.info("   ↳ @RequiresAuth no método? {}", handlerMethod.hasMethodAnnotation(RequiresAuth.class));
        logger.info("   ↳ Requer autenticação? {}", requiresAuth);

        // Se não requer autenticação, deixa passar
        if (!requiresAuth) {
            logger.warn("⚠️ LIBERADO SEM AUTENTICAÇÃO: {} {}", method, requestUri);
            return true;
        }

        // Pega o sessionId do header
        String sessionId = request.getHeader("X-Session-Id");

        logger.info("   ↳ X-Session-Id fornecido? {}", sessionId != null);

        // Se não tem sessionId, bloqueia
        if (sessionId == null || sessionId.isBlank()) {
            logger.error("❌ BLOQUEADO: {} {} - X-Session-Id não fornecido", method, requestUri);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Autenticação necessária. Forneça X-Session-Id no header.\"}");
            response.setContentType("application/json");
            return false;
        }

        // Valida a sessão
        boolean isValid = sessionManager.isValidSession(sessionId);
        logger.info("   ↳ Sessão válida? {}", isValid);

        if (!isValid) {
            logger.error("❌ BLOQUEADO: {} {} - Sessão inválida: {}", method, requestUri, sessionId);
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

        logger.info("✅ LIBERADO COM SUCESSO: {} {} - User: {} ({})", method, requestUri,
                sessionInfo.idUsuario(), sessionInfo.tipoUsuario());

        return true;
    }
}

