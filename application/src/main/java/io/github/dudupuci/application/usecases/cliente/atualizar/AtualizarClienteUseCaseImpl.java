package io.github.dudupuci.application.usecases.cliente.atualizar;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.enums.TipoOperacao;
import io.github.dudupuci.domain.repositories.ClienteRepository;

public class AtualizarClienteUseCaseImpl extends AtualizarClienteUseCase {

    private final ClienteRepository clienteRepository;

    public AtualizarClienteUseCaseImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void execute(AtualizarClienteInput input) {
        // Verifica se o cliente existe
        clienteRepository.buscarPorId(input.id())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + input.id()));

        // Cria Cliente a partir do input
        Cliente clienteAtualizado = input.paraEntidade();

        clienteAtualizado.validar(TipoOperacao.ATUALIZACAO);
        clienteRepository.atualizar(clienteAtualizado);
    }
}

