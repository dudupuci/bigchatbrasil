package io.github.dudupuci.application.usecases.mensagem.criarconversa;

import io.github.dudupuci.domain.enums.TipoUsuario;

import java.util.UUID;

/**
 * Input para criar uma conversa entre dois usuários
 */
public record CriarConversaInput(
        UUID usuarioId1,
        TipoUsuario tipoUsuario1,
        UUID usuarioId2,
        TipoUsuario tipoUsuario2
) {
}

