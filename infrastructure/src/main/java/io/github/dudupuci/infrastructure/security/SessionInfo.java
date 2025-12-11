package io.github.dudupuci.infrastructure.security;

import io.github.dudupuci.domain.enums.TipoUsuario;
import io.github.dudupuci.domain.validators.BcbEntityValidator;

import java.util.UUID;

/**
 * Informações da sessão de um usuário autenticado
 */
public record SessionInfo(UUID idUsuario, TipoUsuario tipoUsuario) {

    public boolean isCliente() {
        return BcbEntityValidator.isTipoUsuarioCliente(this.tipoUsuario);
    }

    public boolean isEmpresa() {
        return BcbEntityValidator.isTipoUsuarioEmpresa(this.tipoUsuario);
    }

    @Override
    public String toString() {
        return "SessionInfo{" +
                "idUsuario=" + idUsuario +
                ", tipoUsuario=" + tipoUsuario +
                '}';
    }
}

