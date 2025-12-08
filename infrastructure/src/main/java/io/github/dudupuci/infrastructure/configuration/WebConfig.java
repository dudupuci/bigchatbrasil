package io.github.dudupuci.infrastructure.configuration;

import io.github.dudupuci.infrastructure.security.SessionInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração Web MVC
 * Registra o SessionInterceptor para validar sessões
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    private final SessionInterceptor sessionInterceptor;

    public WebConfig(SessionInterceptor sessionInterceptor) {
        this.sessionInterceptor = sessionInterceptor;
        logger.info("🔧 WebConfig criado - SessionInterceptor injetado: {}", sessionInterceptor != null);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("🔧 Registrando SessionInterceptor...");
        logger.info("   ↳ Patterns incluídos: /**");
        logger.info("   ↳ Patterns excluídos: /login/**, /registrar/**");

        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")  // ✅ MUDADO: Aplica em TODAS as rotas
                .excludePathPatterns(
                        "/login/**",           // Exclui rotas de login
                        "/registrar/**",       // Exclui rotas de registro
                        "/error"               // Exclui página de erro
                );

        logger.info("✅ SessionInterceptor registrado com sucesso!");
    }
}

