package io.github.dudupuci.application.usecases.empresa.criar;

import io.github.dudupuci.domain.entities.Empresa;

public record CriarEmpresaInput(
        String razaoSocial,
        String cnpj,
        String telefone,
        String email
) {

    public static Empresa criarEntidade(CriarEmpresaInput input) {
        final var empresa = new Empresa();
        empresa.setRazaoSocial(input.razaoSocial());
        empresa.setCnpj(input.cnpj());
        empresa.setTelefone(input.telefone());
        empresa.setEmail(input.email());
        return empresa;
    }

}
