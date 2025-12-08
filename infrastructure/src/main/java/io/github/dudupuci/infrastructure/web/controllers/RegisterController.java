package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteInput;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteOutput;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaOutput;
import io.github.dudupuci.infrastructure.persistence.facade.registrar.RegistrarFacade;
import io.github.dudupuci.infrastructure.web.dtos.request.cliente.CriarClienteApiRequest;
import io.github.dudupuci.infrastructure.web.dtos.request.empresa.CriarEmpresaApiRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/registrar")
@CrossOrigin(origins = "*")
public class RegisterController {

    private final RegistrarFacade registrarFacade;

    public RegisterController(RegistrarFacade registrarFacade) {
        this.registrarFacade = registrarFacade;
    }

    @PostMapping("/cliente")
    public ResponseEntity<?> registrarCliente(@RequestBody CriarClienteApiRequest request) {
        try {
            CriarClienteInput input = request.toApplicationInput();
            CriarClienteOutput output = this.registrarFacade.registrarCliente(input);

            return ResponseEntity.created(URI.create("/auth/register/" + output.id()))
                    .body("Cliente criado com sucesso. ID: " + output.id());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar: " + e.getMessage());
        }
    }

    @PostMapping("/empresa")
    public ResponseEntity<?> registrarEmpresa(@RequestBody CriarEmpresaApiRequest request) {
        try {
            CriarEmpresaInput input = request.toApplicationInput();
            CriarEmpresaOutput output = this.registrarFacade.registrarEmpresa(input);

            return ResponseEntity.created(URI.create("/auth/register/" + output.id()))
                    .body("Empresa criada com sucesso. ID: " + output.id());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar: " + e.getMessage());
        }
    }
}
