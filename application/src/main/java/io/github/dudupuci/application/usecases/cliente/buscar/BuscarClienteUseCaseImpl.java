package io.github.dudupuci.application.usecases.cliente.buscar;

import io.github.dudupuci.domain.repositories.ClienteRepository;

import java.util.UUID;

public class BuscarClienteUseCaseImpl extends BuscarClienteUseCase {

    private final ClienteRepository clienteRepository;

    public BuscarClienteUseCaseImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public BuscarClienteOutput execute(UUID id) {
        try {
            final var cliente = this.clienteRepository.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));

            return BuscarClienteOutput.fromDomain(cliente);

        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }
}

