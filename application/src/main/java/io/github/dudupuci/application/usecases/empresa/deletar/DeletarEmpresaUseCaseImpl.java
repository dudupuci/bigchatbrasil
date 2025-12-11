package io.github.dudupuci.application.usecases.empresa.deletar;

import io.github.dudupuci.domain.repositories.EmpresaRepository;

import java.util.UUID;

public class DeletarEmpresaUseCaseImpl extends DeletarEmpresaUseCase {

    private final EmpresaRepository empresaRepository;

    public DeletarEmpresaUseCaseImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    public void execute(UUID id) {
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

