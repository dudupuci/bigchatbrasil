package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.infrastructure.persistence.facade.login.LoginFacade;
import io.github.dudupuci.infrastructure.security.SimpleSessionManager;
import io.github.dudupuci.infrastructure.web.dtos.request.login.LoginApiRequest;
import io.github.dudupuci.infrastructure.web.dtos.response.login.AuthApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Autenticação SIMPLES - apenas sessionId
 * Senha armazenada com Base64 simples
 **/
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticação", description = "Endpoints para login e logout de usuários (Clientes e Empresas)")
public class LoginController {

    private final LoginFacade loginFacade;
    private final SimpleSessionManager sessionManager;

    public LoginController(
            LoginFacade loginFacade,
            SimpleSessionManager sessionManager
    ) {
        this.loginFacade = loginFacade;
        this.sessionManager = sessionManager;
    }

    @Operation(
            summary = "Login de usuário",
            description = "Autentica um usuário (Cliente ou Empresa) e retorna um token de sessão (X-Session-Id) para uso nos demais endpoints"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthApiResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Credenciais inválidas - Email ou senha incorretos",
                    content = @Content
            )
    })
    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<?> doLogin(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciais de login (email, senha e tipo)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoginApiRequest.class))
            )
            @RequestBody LoginApiRequest request
    ) {
        try {
            var loginOutput = loginFacade.doLogin(request);

            AuthApiResponse response = AuthApiResponse.of(
                    loginOutput.sessionId(),
                    loginOutput.id(),
                    loginOutput.nome(),
                    loginOutput.email(),
                    loginOutput.tipo()
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Credenciais inválidas: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Logout de usuário",
            description = "Invalida a sessão atual do usuário, removendo o token de sessão"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Logout realizado com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao fazer logout ou X-Session-Id não fornecido",
                    content = @Content
            )
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @Parameter(description = "Token de sessão obtido no login", required = true, example = "abc123def456")
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId
    ) {
        try {
            if (sessionId == null || sessionId.isBlank()) {
                return ResponseEntity.badRequest().body("X-Session-Id não fornecido");
            }

            sessionManager.removeSession(sessionId);
            return ResponseEntity.ok("Logout realizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao fazer logout: " + e.getMessage());
        }
    }
}

