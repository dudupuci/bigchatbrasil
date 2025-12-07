package io.github.dudupuci.application.usecases.empresa.criar;

import io.github.dudupuci.domain.enums.TipoOperacao;
import io.github.dudupuci.domain.repositories.EmpresaRepository;

import java.util.Base64;

public class CriarEmpresaUseCaseImpl extends CriarEmpresaUseCase {

    private final EmpresaRepository empresaRepository;

    public CriarEmpresaUseCaseImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    public CriarEmpresaOutput execute(CriarEmpresaInput criarEmpresaInput) {
        try {
            final var empresa = CriarEmpresaInput.criarEntidade(criarEmpresaInput);

            empresa.validar(TipoOperacao.CRIACAO);
            empresa.setSenha(this.encodePassword(empresa.getSenha()));

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

    /**
     * Codifica senha com Base64 simples
     */
    private String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}

