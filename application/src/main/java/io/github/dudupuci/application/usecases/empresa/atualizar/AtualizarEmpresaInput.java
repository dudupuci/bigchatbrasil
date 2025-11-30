package io.github.dudupuci.application.usecases.empresa.atualizar;

import io.github.dudupuci.domain.entities.Empresa;

public record AtualizarEmpresaInput(
        Long id,
        String razaoSocial,
        String cnpj,
        String telefone,
        String email
) {

    public static Empresa criarEntidade(AtualizarEmpresaInput input) {
        final var empresa = new Empresa();
        empresa.setId(input.id());
        empresa.setRazaoSocial(input.razaoSocial());
        empresa.setCnpj(input.cnpj());
        empresa.setTelefone(input.telefone());
        empresa.setEmail(input.email());
        return empresa;
    }

}
