package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.application.usecases.empresa.atualizar.AtualizarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.buscar.BuscarEmpresaInput;
import io.github.dudupuci.infrastructure.persistence.facade.empresas.EmpresaFacade;
import io.github.dudupuci.infrastructure.web.controllers.apidocs.EmpresasControllerAPI;
import io.github.dudupuci.infrastructure.web.dtos.request.empresa.AtualizarEmpresaApiRequest;
import io.github.dudupuci.infrastructure.web.dtos.response.empresa.AtualizarEmpresaApiResponse;
import io.github.dudupuci.infrastructure.web.dtos.response.empresa.BuscarEmpresaApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/empresas")
public class EmpresasController implements EmpresasControllerAPI {

    private final EmpresaFacade empresaFacade;

    public EmpresasController(EmpresaFacade empresaFacade) {
        this.empresaFacade = empresaFacade;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable String id) {
        try {
            BuscarEmpresaApiResponse apiResponse = BuscarEmpresaApiResponse.toApiResponse(
                    this.empresaFacade.buscar(new BuscarEmpresaInput(UUID.fromString(id)))
            );
            return ResponseEntity.ok(apiResponse);
        } catch (Exception err) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody AtualizarEmpresaApiRequest request) {
        try {
            AtualizarEmpresaInput input = request.toApplicationInput(UUID.fromString(id));
            this.empresaFacade.atualizar(input);
            AtualizarEmpresaApiResponse apiResponse = AtualizarEmpresaApiResponse.toApiResponse(UUID.fromString(id), input.razaoSocial());
            return ResponseEntity.ok(apiResponse);
        } catch (Exception err) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable String id) {
        try {
            this.empresaFacade.deletar(UUID.fromString(id));
            return ResponseEntity.noContent().build();
        } catch (Exception err) {
            return ResponseEntity.badRequest().build();
        }
    }
}
