package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.application.usecases.cliente.atualizar.AtualizarClienteInput;
import io.github.dudupuci.infrastructure.persistence.facade.clientes.ClienteFacade;
import io.github.dudupuci.infrastructure.security.annotations.RequiresAuth;
import io.github.dudupuci.infrastructure.web.controllers.apidocs.ClientesControllerAPI;
import io.github.dudupuci.infrastructure.web.dtos.request.cliente.AtualizarClienteApiRequest;
import io.github.dudupuci.infrastructure.web.dtos.response.cliente.AtualizarClienteApiResponse;
import io.github.dudupuci.infrastructure.web.dtos.response.cliente.BuscarClienteApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/clientes")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
@RequiresAuth
@SecurityRequirement(name = "X-Session-Id")
public class ClientesController implements ClientesControllerAPI {

    private final ClienteFacade clienteFacade;

    public ClientesController(ClienteFacade clienteFacade) {
        this.clienteFacade = clienteFacade;
    }

    @Operation(
            summary = "Buscar cliente por ID",
            description = "Retorna os dados completos de um cliente pelo seu identificador único (UUID)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BuscarClienteApiResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token de sessão inválido ou ausente",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "Token de sessão", required = true, example = "abc123def456")
            @RequestHeader("X-Session-Id") String sessionId,
            @Parameter(description = "UUID do cliente", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id
    ) {
        try {
            BuscarClienteApiResponse apiResponse = BuscarClienteApiResponse.toApiResponse(
                    this.clienteFacade.buscarPorId(UUID.fromString(id))
            );
            return ResponseEntity.ok(apiResponse);
        } catch (Exception err) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Atualizar cliente (parcial)",
            description = "Atualiza os dados de um cliente existente. Apenas os campos enviados serão atualizados (PATCH)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AtualizarClienteApiResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou cliente não encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token de sessão inválido ou ausente",
                    content = @Content
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "UUID do cliente", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do cliente a serem atualizados (todos opcionais)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AtualizarClienteApiRequest.class))
            )
            @RequestBody AtualizarClienteApiRequest request
    ) {
        try {
            AtualizarClienteInput input = request.toApplicationInput(UUID.fromString(id));
            this.clienteFacade.atualizar(input);
            AtualizarClienteApiResponse apiResponse = AtualizarClienteApiResponse.toApiResponse(UUID.fromString(id), input.nome());
            return ResponseEntity.ok(apiResponse);
        } catch (Exception err) {
            return ResponseEntity.badRequest().body("Erro ao atualizar: " + err.getMessage());
        }
    }

    @Operation(
            summary = "Deletar cliente",
            description = "Remove um cliente do sistema permanentemente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Cliente deletado com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao deletar cliente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token de sessão inválido ou ausente",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(description = "UUID do cliente", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id
    ) {
        try {
            this.clienteFacade.deletar(UUID.fromString(id));
            return ResponseEntity.noContent().build();
        } catch (Exception err) {
            return ResponseEntity.badRequest().build();
        }
    }
}
