package io.github.dudupuci.application.usecases.cliente.buscar;

import io.github.dudupuci.domain.repositories.ClienteRepository;

public class BuscarClientePorParamsUseCaseImpl extends BuscarClientePorParamsUseCase {

    private final ClienteRepository clienteRepository;

    public BuscarClientePorParamsUseCaseImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public BuscarClienteOutput execute(String email) {
        var cliente = this.clienteRepository.buscarPorEmail(email).orElseThrow(() ->
                new RuntimeException("Cliente não encontrado com o email: " + email));

        return BuscarClienteOutput.fromDomain(cliente);

    }
}

