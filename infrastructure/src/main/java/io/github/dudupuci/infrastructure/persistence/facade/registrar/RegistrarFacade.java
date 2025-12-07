package io.github.dudupuci.infrastructure.persistence.facade.registrar;

import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteInput;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteOutput;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaOutput;

public interface RegistrarFacade {
    CriarClienteOutput registrarCliente(CriarClienteInput input);
    CriarEmpresaOutput registrarEmpresa(CriarEmpresaInput input);
}
