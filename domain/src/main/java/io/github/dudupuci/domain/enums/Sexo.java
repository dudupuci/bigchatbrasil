package io.github.dudupuci.domain.enums;

import lombok.Getter;

public enum Sexo {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    NAO_INFORMADO("Não Informado");

    @Getter
    private final String descricao;

    Sexo(String descricao) {
        this.descricao = descricao;
    }

    public static Sexo fromDescricao(String descricao) {
        for (Sexo sexo : Sexo.values()) {
            if (sexo.getDescricao().equalsIgnoreCase(descricao)) {
                return sexo;
            }
        }
        throw new IllegalArgumentException("Descrição de sexo inválida: " + descricao);
    }

}
