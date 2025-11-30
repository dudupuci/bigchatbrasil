package io.github.dudupuci.application.usecases.cliente.deletar;

import io.github.dudupuci.domain.repositories.ClienteRepository;

public class DeletarClienteUseCaseImpl extends DeletarClienteUseCase {

    private final ClienteRepository clienteRepository;

    public DeletarClienteUseCaseImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void execute(Long id) {
        try {
            // Verifica se o cliente existe antes de deletar
            this.clienteRepository.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));

            this.clienteRepository.deletarPorId(id);

        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }
}

