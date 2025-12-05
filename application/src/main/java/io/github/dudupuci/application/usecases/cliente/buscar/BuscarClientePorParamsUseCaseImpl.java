package io.github.dudupuci.application.usecases.cliente.buscar;

import io.github.dudupuci.application.params.BuscarClienteParams;
import io.github.dudupuci.domain.repositories.ClienteRepository;

public class BuscarClientePorParamsUseCaseImpl extends BuscarClientePorParamsUseCase {

    private final ClienteRepository clienteRepository;

    public BuscarClientePorParamsUseCaseImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public BuscarClienteOutput execute(BuscarClienteParams buscarClienteParams) {
        return null;
    }
}

