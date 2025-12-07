package io.github.dudupuci.application.usecases.cliente.atualizar;

import io.github.dudupuci.domain.enums.TipoOperacao;
import io.github.dudupuci.domain.repositories.ClienteRepository;

public class AtualizarClienteUseCaseImpl extends AtualizarClienteUseCase {

    private final ClienteRepository clienteRepository;

    public AtualizarClienteUseCaseImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void execute(AtualizarClienteInput input) {
        try {
            // Verifica se o cliente existe
            this.clienteRepository.buscarPorId(input.id())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + input.id()));

            final var cliente = AtualizarClienteInput.atualizarEntidade(input);

            cliente.validar(TipoOperacao.ATUALIZACAO);
            this.clienteRepository.atualizar(cliente);

        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }
}

