package io.github.dudupuci.domain.entities.base;

import io.github.dudupuci.domain.enums.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/*
Se eu tivesse mais tempo, usuaria essa classe UsuarioApp
como base para as outras classes de usuário
(Cliente, Empresa, SuperAdmin),

Sendo que cada entidade logável poderia ter um objeto UsuarioApp.
 */
@Getter
@Setter
public class UsuarioApp {
    private UUID id;
    private TipoUsuario tipoUsuario;
    private String email;
    private String senha;
}
