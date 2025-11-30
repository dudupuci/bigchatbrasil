package io.github.dudupuci.infrastructure.persistence.facade;

import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteInput;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteOutput;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteUseCase;
import org.springframework.stereotype.Service;

@Service
public class ClienteFacadeImpl implements ClienteFacade{

    private final CriarClienteUseCase criarClienteUseCase;

    public ClienteFacadeImpl(CriarClienteUseCase criarClienteUseCase) {
        this.criarClienteUseCase = criarClienteUseCase;
    }

    @Override
    public CriarClienteOutput criar(CriarClienteInput input) {
        return this.criarClienteUseCase.execute(input);
    }
}
