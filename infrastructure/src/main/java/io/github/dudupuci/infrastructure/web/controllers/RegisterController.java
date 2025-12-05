package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.infrastructure.web.dtos.request.RegistrarClienteRequest;
import io.github.dudupuci.infrastructure.web.dtos.request.RegistrarEmpresaRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Base64;


@RestController
@RequestMapping("/registrar")
public class RegisterController {


    /**
     * Codifica senha com Base64 simples
     */
    private String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }


    @PostMapping("/cliente")
    public ResponseEntity<?> register(@RequestBody RegistrarClienteRequest request) {
        try {

            return ResponseEntity.created(URI.create("/auth/register/" + 1L))
                    .body("Cliente criado com sucesso. ID: " + 1L);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar: " + e.getMessage());
        }
    }

    @PostMapping("/empresa")
    public ResponseEntity<?> register(@RequestBody RegistrarEmpresaRequest request) {
        try {

            return ResponseEntity.created(URI.create("/auth/register/" + 1L))
                    .body("Empresa criada com sucesso. ID: " + 1L);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar: " + e.getMessage());
        }
    }
}
