package io.github.dudupuci.application.usecases.empresa.buscar;

import io.github.dudupuci.domain.entities.Empresa;

public record BuscarEmpresaOutput(
        Long id,
        String razaoSocial,
        String cnpj,
        String telefone,
        String email,
        String senha
) {

    public static BuscarEmpresaOutput fromDomain(Empresa empresa) {
        return new BuscarEmpresaOutput(
                empresa.getId(),
                empresa.getRazaoSocial(),
                empresa.getCnpj(),
                empresa.getTelefone(),
                empresa.getEmail(),
                null
        );
    }

    public Empresa toDomain() {
        Empresa empresa = new Empresa();
        empresa.setId(this.id);
        empresa.setRazaoSocial(this.razaoSocial);
        empresa.setCnpj(this.cnpj);
        empresa.setTelefone(this.telefone);
        empresa.setEmail(this.email);
        return empresa;
    }
}

