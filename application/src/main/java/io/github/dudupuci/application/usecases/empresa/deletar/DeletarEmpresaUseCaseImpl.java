package io.github.dudupuci.application.usecases.empresa.deletar;

import io.github.dudupuci.domain.repositories.EmpresaRepository;

public class DeletarEmpresaUseCaseImpl extends DeletarEmpresaUseCase {

    private final EmpresaRepository empresaRepository;

    public DeletarEmpresaUseCaseImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    public void execute(Long id) {
        try {
            // Verifica se a empresa existe antes de deletar
            this.empresaRepository.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + id));

            this.empresaRepository.deletarPorId(id);

        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }
}

