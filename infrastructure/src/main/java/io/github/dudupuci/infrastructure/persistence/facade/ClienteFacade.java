package io.github.dudupuci.infrastructure.persistence.facade;

import io.github.dudupuci.application.usecases.cliente.atualizar.AtualizarClienteInput;
import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClienteOutput;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteInput;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteOutput;

public interface ClienteFacade {
    CriarClienteOutput criar(CriarClienteInput input);
    void atualizar(AtualizarClienteInput input);
    BuscarClienteOutput buscar(Long input);
    BuscarClienteOutput buscarPor(String param);
    void deletar(Long input);
}
