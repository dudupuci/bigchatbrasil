package io.github.dudupuci.application.usecases.empresa.criar;

import io.github.dudupuci.domain.repositories.EmpresaRepository;

public class CriarEmpresaUseCaseImpl extends CriarEmpresaUseCase {

    private final EmpresaRepository empresaRepository;

    public CriarEmpresaUseCaseImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    public CriarEmpresaOutput execute(CriarEmpresaInput criarEmpresaInput) {
        try {
            final var empresa = CriarEmpresaInput.criarEntidade(criarEmpresaInput);

            empresa.validar();
            final var empresaCriada = this.empresaRepository.salvar(empresa);

            return new CriarEmpresaOutput(
                    empresaCriada.getId(),
                    empresaCriada.getRazaoSocial(),
                    true
            );

        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }
}

