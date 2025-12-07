package io.github.dudupuci.application.usecases.empresa.buscar;

import io.github.dudupuci.domain.repositories.EmpresaRepository;

public class BuscarEmpresaUseCaseImpl extends BuscarEmpresaUseCase {

    private final EmpresaRepository empresaRepository;

    public BuscarEmpresaUseCaseImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    public BuscarEmpresaOutput execute(BuscarEmpresaInput buscarEmpresaInput) {
        try {
            final var empresa = this.empresaRepository.buscarPorId(buscarEmpresaInput.id())
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + buscarEmpresaInput.id()));

            return BuscarEmpresaOutput.fromDomain(empresa);

        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }
}

