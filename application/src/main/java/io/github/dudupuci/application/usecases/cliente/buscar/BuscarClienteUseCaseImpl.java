package io.github.dudupuci.application.usecases.cliente.buscar;

import io.github.dudupuci.domain.repositories.ClienteRepository;

public class BuscarClienteUseCaseImpl extends BuscarClienteUseCase {

    private final ClienteRepository clienteRepository;

    public BuscarClienteUseCaseImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public BuscarClienteOutput execute(BuscarClienteInput buscarClienteInput) {
        try {
            final var cliente = this.clienteRepository.buscarPorId(buscarClienteInput.id())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + buscarClienteInput.id()));

            return new BuscarClienteOutput(
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getSexo(),
                    cliente.getEmail(),
                    cliente.getDocumento(),
                    cliente.getTelefone(),
                    cliente.getSobre()
            );

        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }
}

