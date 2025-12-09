package io.github.dudupuci.application.usecases.mensagem.criarconversa;

import io.github.dudupuci.domain.enums.TipoUsuario;

/**
 * Input para criar uma conversa entre dois usuários
 */
public record CriarConversaInput(
        Long usuarioId1,
        TipoUsuario tipoUsuario1,
        Long usuarioId2,
        TipoUsuario tipoUsuario2
) {
}

