package io.github.dudupuci.domain.entities;

import io.github.dudupuci.domain.entities.base.Entidade;
import io.github.dudupuci.domain.enums.TipoConversa;
import io.github.dudupuci.domain.enums.TipoOperacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidade de Conversa - representa uma conversa entre dois ou mais usuários
 */
@Getter
@Setter
@NoArgsConstructor
public class Conversa extends Entidade {
    private UUID conversaId;
    private Long usuario1Id;
    private Long usuario2Id;
    private TipoConversa tipo;
    private Instant criadaEm;
    private Instant ultimaAtualizacao;
    private List<Mensagem> mensagens;

    public void adicionarMensagem(Mensagem mensagem) {
        if (this.mensagens == null) {
            this.mensagens = new ArrayList<>();
        }
        this.mensagens.add(mensagem);
        this.ultimaAtualizacao = Instant.now();
    }

    public boolean isParticipante(Long usuarioId) {
        return this.usuario1Id.equals(usuarioId) || this.usuario2Id.equals(usuarioId);
    }

    @Override
    public void validar(TipoOperacao tipoOperacao) {
        if (conversaId == null) {
            throw new IllegalArgumentException("ConversaId não pode ser nulo");
        }
        if (usuario1Id == null || usuario2Id == null) {
            throw new IllegalArgumentException("Participantes da conversa não podem ser nulos");
        }
        if (usuario1Id.equals(usuario2Id)) {
            throw new IllegalArgumentException("Uma conversa não pode ter o mesmo usuário duas vezes");
        }
    }
}

