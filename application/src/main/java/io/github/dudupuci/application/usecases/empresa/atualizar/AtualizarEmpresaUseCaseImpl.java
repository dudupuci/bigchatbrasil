package io.github.dudupuci.application.usecases.empresa.atualizar;

import io.github.dudupuci.domain.repositories.EmpresaRepository;

public class AtualizarEmpresaUseCaseImpl extends AtualizarEmpresaUseCase {

    private final EmpresaRepository empresaRepository;

    public AtualizarEmpresaUseCaseImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    public void execute(AtualizarEmpresaInput atualizarEmpresaInput) {
        try {
            final var empresa = AtualizarEmpresaInput.criarEntidade(atualizarEmpresaInput);

            // Verifica se a empresa existe
            this.empresaRepository.buscarPorId(empresa.getId())
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + empresa.getId()));

            empresa.validar();
            this.empresaRepository.atualizar(empresa);

        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }
}

