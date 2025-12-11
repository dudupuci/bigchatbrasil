package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteInput;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteOutput;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaOutput;
import io.github.dudupuci.infrastructure.persistence.facade.registrar.RegistrarFacade;
import io.github.dudupuci.infrastructure.web.dtos.request.cliente.CriarClienteApiRequest;
import io.github.dudupuci.infrastructure.web.dtos.request.empresa.CriarEmpresaApiRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/registrar")
@CrossOrigin(origins = "*")
@Tag(name = "Registro", description = "Endpoints para cadastro de novos usuários (Clientes e Empresas)")
public class RegisterController {

    private final RegistrarFacade registrarFacade;

    public RegisterController(RegistrarFacade registrarFacade) {
        this.registrarFacade = registrarFacade;
    }

    @Operation(
            summary = "Registrar cliente",
            description = "Cadastra um novo cliente no sistema. " +
                    "Requer dados pessoais, CPF/CNPJ, email único e senha."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente criado com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro de validação - Dados inválidos ou email já cadastrado",
                    content = @Content
            )
    })
    @PostMapping("/cliente")
    public ResponseEntity<?> registrarCliente(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do cliente a ser cadastrado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CriarClienteApiRequest.class))
            )
            @RequestBody CriarClienteApiRequest request
    ) {
        try {
            CriarClienteInput input = request.toApplicationInput();
            CriarClienteOutput output = this.registrarFacade.registrarCliente(input);

            return ResponseEntity.created(URI.create("/auth/register/" + output.id()))
                    .body("Cliente criado com sucesso. ID: " + output.id());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Registrar empresa",
            description = "Cadastra uma nova empresa no sistema. " +
                    "Requer razão social, CNPJ, email único e senha."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Empresa criada com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro de validação - Dados inválidos ou email já cadastrado",
                    content = @Content
            )
    })
    @PostMapping("/empresa")
    public ResponseEntity<?> registrarEmpresa(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da empresa a ser cadastrada",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CriarEmpresaApiRequest.class))
            )
            @RequestBody CriarEmpresaApiRequest request
    ) {
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
