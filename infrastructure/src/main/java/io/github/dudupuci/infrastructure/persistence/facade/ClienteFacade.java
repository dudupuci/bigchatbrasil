package io.github.dudupuci.infrastructure.persistence.facade;

import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteInput;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteOutput;

public interface ClienteFacade {
    CriarClienteOutput criar(CriarClienteInput input);
}
