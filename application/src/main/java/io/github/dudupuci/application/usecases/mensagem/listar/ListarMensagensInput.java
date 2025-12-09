package io.github.dudupuci.application.usecases.mensagem.listar;

import java.util.UUID;

/**
 * Input para listar mensagens de uma conversa
 */
public record ListarMensagensInput(
        UUID conversaId,
        Long usuarioId // Para validar que o usuário pertence à conversa
) {
}

