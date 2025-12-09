package io.github.dudupuci.application.usecases.mensagem.listar;

import java.util.List;
import java.util.UUID;

/**
 * Output da listagem de mensagens de uma conversa
 */
public record ListarMensagensOutput(
        UUID conversaId,
        List<MensagemDto> mensagens,
        int total
) {
}

