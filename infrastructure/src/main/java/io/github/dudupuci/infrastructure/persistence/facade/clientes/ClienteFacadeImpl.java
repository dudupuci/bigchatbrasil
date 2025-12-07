package io.github.dudupuci.infrastructure.persistence.facade.clientes;

import io.github.dudupuci.application.usecases.cliente.atualizar.AtualizarClienteInput;
import io.github.dudupuci.application.usecases.cliente.atualizar.AtualizarClienteUseCase;
import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClienteOutput;
import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClientePorParamsUseCase;
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
    private final DeletarClienteUseCase deletarClienteUseCase;
    private final BuscarClientePorParamsUseCase buscarClientePorParamsUseCase;
    private final BuscarClienteUseCase buscarClienteUseCase;

    public ClienteFacadeImpl(
            CriarClienteUseCase criarClienteUseCase,
            AtualizarClienteUseCase atualizarClienteUseCase,
            DeletarClienteUseCase deletarClienteUseCase,
            BuscarClientePorParamsUseCase buscarClientePorParamsUseCase,
            BuscarClienteUseCase buscarClienteUseCase
    ) {
        this.criarClienteUseCase = criarClienteUseCase;
        this.atualizarClienteUseCase = atualizarClienteUseCase;
        this.deletarClienteUseCase = deletarClienteUseCase;
        this.buscarClientePorParamsUseCase = buscarClientePorParamsUseCase;
        this.buscarClienteUseCase = buscarClienteUseCase;
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
    public BuscarClienteOutput buscarPorEmail(String email) {
        return this.buscarClientePorParamsUseCase.execute(email);
    }

    @Override
    public BuscarClienteOutput buscarPorId(Long id) {
        return this.buscarClienteUseCase.execute(id);
    }

    @Override
    public void deletar(Long input) {
        this.deletarClienteUseCase.execute(input);
    }
}
