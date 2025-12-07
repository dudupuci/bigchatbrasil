package io.github.dudupuci.application.usecases.empresa.buscar;

import io.github.dudupuci.domain.repositories.EmpresaRepository;

public class BuscarEmpresaPorParamsUseCaseImpl extends BuscarEmpresaPorParamsUseCase {

    private final EmpresaRepository empresaRepository;

    public BuscarEmpresaPorParamsUseCaseImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }


    @Override
    public BuscarEmpresaOutput execute(String email) {
        var empresa =  this.empresaRepository.buscarPorEmail(email).orElseThrow(() ->
                new RuntimeException("Empresa não encontrada com o email: " + email));

        return BuscarEmpresaOutput.fromDomain(empresa);
    }
}

