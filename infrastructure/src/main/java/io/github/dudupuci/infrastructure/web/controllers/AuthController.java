package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClienteOutput;
import io.github.dudupuci.infrastructure.persistence.facade.ClienteFacade;
import io.github.dudupuci.infrastructure.persistence.facade.EmpresaFacade;
import io.github.dudupuci.infrastructure.security.SimpleSessionManager;
import io.github.dudupuci.infrastructure.web.dtos.request.LoginRequest;
import io.github.dudupuci.infrastructure.web.dtos.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Base64;

/**
 * Autenticação SIMPLES - apenas sessionId
 * Senha armazenada com Base64 simples (para demo/desafio)
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ClienteFacade clienteFacade;
    private final EmpresaFacade empresaFacade;
    private final SimpleSessionManager sessionManager;

    public AuthController(
            ClienteFacade clienteFacade,
            EmpresaFacade empresaFacade,
            SimpleSessionManager sessionManager
    ) {
        this.clienteFacade = clienteFacade;
        this.empresaFacade = empresaFacade;
        this.sessionManager = sessionManager;
    }

    /**
     * Valida senha comparando com a codificada
     */
    private boolean matchPassword(String rawPassword, String encodedPassword) {
        return encodePassword(rawPassword).equals(encodedPassword);
    }

    /**
     * Codifica senha com Base64 simples
     */
    private String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            BuscarClienteOutput output = clienteFacade.buscarPor(request.email());

            // Valida senha
            if (!matchPassword(request.senha(), output.senha())) {
                return ResponseEntity.badRequest().body("Credenciais inválidas");
            }

            // Cria sessão simples
            String sessionId = sessionManager.createSession(output.id());

            return ResponseEntity.ok(AuthResponse.of(sessionId, output.email(), output.id()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Credenciais inválidas");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
        try {
            if (sessionManager.isValidSession(sessionId)) {
                Long clienteId = sessionManager.getClienteId(sessionId);
                var cliente = clienteRepository.findById(clienteId)
                        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

                return ResponseEntity.ok(AuthResponse.of(sessionId, cliente.getEmail(), cliente.getId()));
            }
            return ResponseEntity.status(401).body("Sessão inválida");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Sessão inválida: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
        try {
            sessionManager.removeSession(sessionId);
            return ResponseEntity.ok("Logout realizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao fazer logout");
        }
    }
}

