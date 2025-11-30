package io.github.dudupuci.application.usecases.cliente.atualizar;

import io.github.dudupuci.domain.repositories.ClienteRepository;

public class AtualizarClienteUseCaseImpl extends AtualizarClienteUseCase {

    private final ClienteRepository clienteRepository;

    public AtualizarClienteUseCaseImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void execute(AtualizarClienteInput atualizarClienteInput) {
        try {
            final var cliente = AtualizarClienteInput.criarEntidade(atualizarClienteInput);

            // Verifica se o cliente existe
            this.clienteRepository.buscarPorId(cliente.getId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + cliente.getId()));

            cliente.validar();
            this.clienteRepository.atualizar(cliente);

        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }
}

