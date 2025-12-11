package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.application.usecases.empresa.atualizar.AtualizarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.buscar.BuscarEmpresaInput;
import io.github.dudupuci.infrastructure.persistence.facade.empresas.EmpresaFacade;
import io.github.dudupuci.infrastructure.web.controllers.apidocs.EmpresasControllerAPI;
import io.github.dudupuci.infrastructure.web.dtos.request.empresa.AtualizarEmpresaApiRequest;
import io.github.dudupuci.infrastructure.web.dtos.response.empresa.AtualizarEmpresaApiResponse;
import io.github.dudupuci.infrastructure.web.dtos.response.empresa.BuscarEmpresaApiResponse;
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
@RequestMapping(path = "/empresas")
@Tag(name = "Empresas", description = "Endpoints para gerenciamento de empresas")
@SecurityRequirement(name = "X-Session-Id")
public class EmpresasController implements EmpresasControllerAPI {

    private final EmpresaFacade empresaFacade;

    public EmpresasController(EmpresaFacade empresaFacade) {
        this.empresaFacade = empresaFacade;
    }

    @Operation(
            summary = "Buscar empresa por ID",
            description = "Retorna os dados completos de uma empresa pelo seu identificador único (UUID)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Empresa encontrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BuscarEmpresaApiResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Empresa não encontrada",
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
            @Parameter(description = "UUID da empresa", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id
    ) {
        try {
            BuscarEmpresaApiResponse apiResponse = BuscarEmpresaApiResponse.toApiResponse(
                    this.empresaFacade.buscar(new BuscarEmpresaInput(UUID.fromString(id)))
            );
            return ResponseEntity.ok(apiResponse);
        } catch (Exception err) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Atualizar empresa",
            description = "Atualiza os dados de uma empresa existente. Campos não enviados permanecerão inalterados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Empresa atualizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AtualizarEmpresaApiResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou empresa não encontrada",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token de sessão inválido ou ausente",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "UUID da empresa", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da empresa a serem atualizados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AtualizarEmpresaApiRequest.class))
            )
            @RequestBody AtualizarEmpresaApiRequest request
    ) {
        try {
            AtualizarEmpresaInput input = request.toApplicationInput(UUID.fromString(id));
            this.empresaFacade.atualizar(input);
            AtualizarEmpresaApiResponse apiResponse = AtualizarEmpresaApiResponse.toApiResponse(UUID.fromString(id), input.razaoSocial());
            return ResponseEntity.ok(apiResponse);
        } catch (Exception err) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Deletar empresa",
            description = "Remove uma empresa do sistema permanentemente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Empresa deletada com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao deletar empresa",
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
            @Parameter(description = "UUID da empresa", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id
    ) {
        try {
            this.empresaFacade.deletar(UUID.fromString(id));
            return ResponseEntity.noContent().build();
        } catch (Exception err) {
            return ResponseEntity.badRequest().build();
        }
    }
}
