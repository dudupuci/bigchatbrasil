package io.github.dudupuci.infrastructure.configuration;

import io.github.dudupuci.domain.constants.BcbConstants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.github.dudupuci.domain.constants.BcbConstants.*;

/**
 * Configuração do Swagger/OpenAPI para documentação da API
 * <p>
 * URLs de Acesso:
 * - Swagger UI: <a href="http://localhost:8081/api/swagger-ui.html">...</a>
 * - API Docs JSON: <a href="http://localhost:8081/api/v3/api-docs">...</a>
 * - API Docs YAML: <a href="http://localhost:8081/api/v3/api-docs.yaml">...</a>
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(NOME_APLICACAO)
                        .version(VERSAO_APLICACAO)
                        .description(DESCRICAO_APLICACAO)
                        .contact(new Contact()
                                .name(NOME_STAFF_APLICACAO)
                                .email(EMAIL_CONTATO)
                                .url(GITHUB_URL_REPOSITORIO))
                        .license(new License()
                                .name(LICENSA)
                                .url(URL_LICENSA)))
                .components(new Components()
                        .addSecuritySchemes(X_SESSION_ID, new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(X_SESSION_ID)
                                .description("Token de sessão obtido após login")))
                .addSecurityItem(new SecurityRequirement().addList(X_SESSION_ID));
    }
}

