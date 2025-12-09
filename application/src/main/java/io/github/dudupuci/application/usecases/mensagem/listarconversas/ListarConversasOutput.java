package io.github.dudupuci.application.usecases.mensagem.listarconversas;

import java.util.List;

/**
 * Output com lista de conversas do usuário
 */
public record ListarConversasOutput(
        List<ConversaDto> conversas,
        int total
) {
}

