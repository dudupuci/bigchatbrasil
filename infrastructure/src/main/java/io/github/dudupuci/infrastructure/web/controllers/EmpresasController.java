package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.application.usecases.empresa.atualizar.AtualizarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.buscar.BuscarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaInput;
import io.github.dudupuci.infrastructure.persistence.facade.EmpresaFacade;
import io.github.dudupuci.infrastructure.web.controllers.apidocs.EmpresasControllerAPI;
import io.github.dudupuci.infrastructure.web.dtos.request.AtualizarEmpresaApiRequest;
import io.github.dudupuci.infrastructure.web.dtos.request.CriarEmpresaApiRequest;
import io.github.dudupuci.infrastructure.web.dtos.response.AtualizarEmpresaApiResponse;
import io.github.dudupuci.infrastructure.web.dtos.response.BuscarEmpresaApiResponse;
import io.github.dudupuci.infrastructure.web.dtos.response.CriarEmpresaApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/empresas")
public class EmpresasController implements EmpresasControllerAPI {

    private final EmpresaFacade empresaFacade;

    public EmpresasController(EmpresaFacade empresaFacade) {
        this.empresaFacade = empresaFacade;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody CriarEmpresaApiRequest request) {
        try {
            CriarEmpresaInput input = request.toApplicationInput();
            CriarEmpresaApiResponse apiResponse = CriarEmpresaApiResponse.toApiResponse(
                    this.empresaFacade.criar(input)
            );
            return ResponseEntity.ok(apiResponse);
        } catch (Exception err) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            BuscarEmpresaInput input = new BuscarEmpresaInput(id);
            BuscarEmpresaApiResponse apiResponse = BuscarEmpresaApiResponse.toApiResponse(
                    this.empresaFacade.buscar(input)
            );
            return ResponseEntity.ok(apiResponse);
        } catch (Exception err) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AtualizarEmpresaApiRequest request) {
        try {
            AtualizarEmpresaInput input = request.toApplicationInput(id);
            this.empresaFacade.atualizar(input);
            AtualizarEmpresaApiResponse apiResponse = AtualizarEmpresaApiResponse.toApiResponse(id, input.razaoSocial());
            return ResponseEntity.ok(apiResponse);
        } catch (Exception err) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            this.empresaFacade.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception err) {
            return ResponseEntity.badRequest().build();
        }
    }
}
