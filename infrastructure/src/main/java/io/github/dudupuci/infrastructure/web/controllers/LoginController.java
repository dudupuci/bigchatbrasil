package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.infrastructure.persistence.facade.login.LoginFacade;
import io.github.dudupuci.infrastructure.security.SimpleSessionManager;
import io.github.dudupuci.infrastructure.web.dtos.request.login.LoginApiRequest;
import io.github.dudupuci.infrastructure.web.dtos.response.login.AuthApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Autenticação SIMPLES - apenas sessionId
 * Senha armazenada com Base64 simples
 **/
@RestController
@RequestMapping
@CrossOrigin(origins = "*")
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

    /**
     * Login para usuários
     * Retorna sessionId no BODY (JSON)
     */
    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody LoginApiRequest request) {
        try {
            String sessionId = loginFacade.doLogin(request);
            return ResponseEntity.ok(AuthApiResponse.of(sessionId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Credenciais inválidas: " + e.getMessage());
        }
    }

    /**
     * Logout - invalida a sessão
     * Endpoint público - permite logout mesmo com sessão inválida
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
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

