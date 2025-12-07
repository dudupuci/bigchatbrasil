package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.application.usecases.cliente.atualizar.AtualizarClienteInput;
import io.github.dudupuci.infrastructure.persistence.facade.clientes.ClienteFacade;
import io.github.dudupuci.infrastructure.web.controllers.apidocs.ClientesControllerAPI;
import io.github.dudupuci.infrastructure.web.dtos.request.cliente.AtualizarClienteApiRequest;
import io.github.dudupuci.infrastructure.web.dtos.response.cliente.AtualizarClienteApiResponse;
import io.github.dudupuci.infrastructure.web.dtos.response.cliente.BuscarClienteApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/clientes")
public class ClientesController implements ClientesControllerAPI {

    private final ClienteFacade clienteFacade;

    public ClientesController(ClienteFacade clienteFacade) {
        this.clienteFacade = clienteFacade;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            BuscarClienteApiResponse apiResponse = BuscarClienteApiResponse.toApiResponse(
                    this.clienteFacade.buscarPorId(id)
            );
            return ResponseEntity.ok(apiResponse);
        } catch (Exception err) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AtualizarClienteApiRequest request) {
        try {
            AtualizarClienteInput input = request.toApplicationInput(id);
            this.clienteFacade.atualizar(input);
            AtualizarClienteApiResponse apiResponse = AtualizarClienteApiResponse.toApiResponse(id, input.nome());
            return ResponseEntity.ok(apiResponse);
        } catch (Exception err) {
            return ResponseEntity.badRequest().body("Erro ao atualizar: " + err.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            this.clienteFacade.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception err) {
            return ResponseEntity.badRequest().build();
        }
    }
}
