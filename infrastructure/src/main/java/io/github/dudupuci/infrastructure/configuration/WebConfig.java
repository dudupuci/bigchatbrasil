package io.github.dudupuci.infrastructure.configuration;

import io.github.dudupuci.infrastructure.security.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração Web MVC
 * Registra o SessionInterceptor para validar sessões
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SessionInterceptor sessionInterceptor;

    public WebConfig(SessionInterceptor sessionInterceptor) {
        this.sessionInterceptor = sessionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/api/**")  // Aplica em todas as rotas /api
                .excludePathPatterns(
                        "/api/login/**",      // Exclui rotas de login
                        "/api/registrar/**"   // Exclui rotas de registro
                );
    }
}

