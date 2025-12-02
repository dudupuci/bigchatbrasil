package io.github.dudupuci.domain.enums;

public enum TipoDocumento {
    CPF("Cpf"),
    CNPJ("Cnpj");

    private final String descricao;

    TipoDocumento(String descricao) {
        this.descricao = descricao;
    }

    public TipoDocumento fromDescricao(String descricao) {
        for (TipoDocumento tipo : TipoDocumento.values()) {
            if (tipo.descricao.equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Descrição de tipo de documento inválida: " + descricao);
    }
}
