package io.github.dudupuci.infrastructure.persistence.facade.clientes;

import io.github.dudupuci.application.usecases.cliente.atualizar.AtualizarClienteInput;
import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClienteOutput;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteInput;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteOutput;

import java.util.UUID;

public interface ClienteFacade {
    CriarClienteOutput criar(CriarClienteInput input);
    void atualizar(AtualizarClienteInput input);
    BuscarClienteOutput buscarPorEmail(String email);
    BuscarClienteOutput buscarPorId(UUID id);
    void deletar(UUID input);
}
