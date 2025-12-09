package io.github.dudupuci.application.usecases.mensagem.criarconversa;

import java.util.UUID;

/**
 * Output com o ID da conversa criada
 */
public record CriarConversaOutput(
        UUID conversaId
) {
}

