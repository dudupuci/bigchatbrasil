package io.github.dudupuci.infrastructure.web.dtos.request.mensagem;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemInput;
import io.github.dudupuci.domain.enums.TipoUsuario;

import java.util.UUID;

/**
 * Request para enviar mensagem
 * conversaId é OPCIONAL - se não enviado, será gerado automaticamente
 * tipoDestinatario é OBRIGATÓRIO para validar que remetente != destinatário
 */
public record EnviarMensagemApiRequest(
        UUID conversaId,
        UUID destinatarioId,
        String tipoDestinatario, // "CLIENTE" ou "EMPRESA"
        String conteudo,
        String tipo,
        String prioridade
) {

    public EnviarMensagemInput toApplicationInput(UUID remetenteId, TipoUsuario tipoRemetente) {
        return new EnviarMensagemInput(
                this.conversaId,
                remetenteId,
                tipoRemetente,
                this.destinatarioId,
                TipoUsuario.valueOf(this.tipoDestinatario),
                this.conteudo,
                this.tipo,
                this.prioridade
        );
    }
}

