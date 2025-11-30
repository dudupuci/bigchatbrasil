package io.github.dudupuci.infrastructure.persistence.facade;

import io.github.dudupuci.application.usecases.cliente.atualizar.AtualizarClienteInput;
import io.github.dudupuci.application.usecases.cliente.atualizar.AtualizarClienteUseCase;
import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClienteOutput;
import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClienteUseCase;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteInput;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteOutput;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteUseCase;
import io.github.dudupuci.application.usecases.cliente.deletar.DeletarClienteUseCase;
import org.springframework.stereotype.Service;

@Service
public class ClienteFacadeImpl implements ClienteFacade {

    private final CriarClienteUseCase criarClienteUseCase;
    private final AtualizarClienteUseCase atualizarClienteUseCase;
    private final BuscarClienteUseCase buscarClienteUseCase;
    private final DeletarClienteUseCase deletarClienteUseCase;

    public ClienteFacadeImpl(
            CriarClienteUseCase criarClienteUseCase,
            AtualizarClienteUseCase atualizarClienteUseCase,
            BuscarClienteUseCase buscarClienteUseCase,
            DeletarClienteUseCase deletarClienteUseCase
    ) {
        this.criarClienteUseCase = criarClienteUseCase;
        this.atualizarClienteUseCase = atualizarClienteUseCase;
        this.buscarClienteUseCase = buscarClienteUseCase;
        this.deletarClienteUseCase = deletarClienteUseCase;
    }

    @Override
    public CriarClienteOutput criar(CriarClienteInput input) {
        return this.criarClienteUseCase.execute(input);
    }

    @Override
    public void atualizar(AtualizarClienteInput input) {
        this.atualizarClienteUseCase.execute(input);
    }

    @Override
    public BuscarClienteOutput buscar(Long input) {
        return this.buscarClienteUseCase.execute(input);
    }

    @Override
    public void deletar(Long input) {
        this.deletarClienteUseCase.execute(input);
    }
}
