package io.github.dudupuci.infrastructure.persistence.facade.registrar;

import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteInput;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteOutput;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaOutput;
import io.github.dudupuci.infrastructure.persistence.facade.clientes.ClienteFacade;
import io.github.dudupuci.infrastructure.persistence.facade.empresas.EmpresaFacade;
import org.springframework.stereotype.Service;

@Service
public class RegistrarFacadeImpl implements RegistrarFacade {

    private final ClienteFacade clienteFacade;
    private final EmpresaFacade empresaFacade;

    public RegistrarFacadeImpl(ClienteFacade clienteFacade, EmpresaFacade empresaFacade) {
        this.clienteFacade = clienteFacade;
        this.empresaFacade = empresaFacade;
    }

    @Override
    public CriarClienteOutput registrarCliente(CriarClienteInput input) {
        return this.clienteFacade.criar(input);
    }

    @Override
    public CriarEmpresaOutput registrarEmpresa(CriarEmpresaInput input) {
        return this.empresaFacade.criar(input);
    }
}
