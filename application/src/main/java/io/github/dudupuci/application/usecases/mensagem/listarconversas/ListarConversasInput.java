package io.github.dudupuci.application.usecases.mensagem.listarconversas;

import io.github.dudupuci.domain.enums.TipoUsuario;

import java.util.UUID;

/**
 * Input para listar conversas de um usuário
 */
public record ListarConversasInput(
        UUID usuarioId,
        TipoUsuario tipoUsuario
) {
}

