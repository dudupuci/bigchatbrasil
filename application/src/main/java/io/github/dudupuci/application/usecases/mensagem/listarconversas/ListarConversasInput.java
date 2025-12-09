package io.github.dudupuci.application.usecases.mensagem.listarconversas;

import io.github.dudupuci.domain.enums.TipoUsuario;

/**
 * Input para listar conversas de um usuário
 */
public record ListarConversasInput(
        Long usuarioId,
        TipoUsuario tipoUsuario
) {
}

