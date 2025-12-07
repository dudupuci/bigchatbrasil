package io.github.dudupuci.domain.validators;

import io.github.dudupuci.domain.entities.base.Entidade;
import io.github.dudupuci.domain.enums.TipoUsuario;

public final class BcbEntityValidator {

    public static boolean isEntityNotNull(Entidade entidade) {
        return entidade != null && entidade.getId() != null;
    }

    public static boolean isTipoUsuarioEmpresa(TipoUsuario tipoUsuario) {
        return tipoUsuario == TipoUsuario.EMPRESA;
    }

    public static boolean isTipoUsuarioCliente(TipoUsuario tipoUsuario) {
        return tipoUsuario == TipoUsuario.CLIENTE;
    }
}
