package io.github.dudupuci.application.usecases.mensagem.enviar;

import io.github.dudupuci.domain.entities.Mensagem;
import io.github.dudupuci.domain.enums.PrioridadeNotificacao;
import io.github.dudupuci.domain.enums.StatusNotificacao;
import io.github.dudupuci.domain.enums.TipoNotificacao;
import io.github.dudupuci.domain.enums.TipoUsuario;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.UUID;

public record EnviarMensagemInput(
        UUID conversaId,
        Long remetenteId,
        TipoUsuario tipoRemetente,
        Long destinatarioId,
        TipoUsuario tipoDestinatario,
        String conteudo,
        String tipo,
        String prioridade
) {

    public static Mensagem criarEntidade(EnviarMensagemInput input) {
        final var mensagem = new Mensagem();

        // ✅ GERA conversaId automaticamente se não for fornecido
        UUID conversaIdFinal = input.conversaId();
        if (conversaIdFinal == null) {
            conversaIdFinal = gerarConversaId(input.remetenteId(), input.destinatarioId());
        }

        mensagem.setDataCriacao(Instant.now());
        mensagem.setDataAtualizacao(Instant.now());
        mensagem.setConversaId(conversaIdFinal);
        mensagem.setRemetenteId(input.remetenteId());
        mensagem.setDestinatarioId(input.destinatarioId());
        mensagem.setConteudo(input.conteudo());
        mensagem.setTipo(TipoNotificacao.valueOf(input.tipo()));
        mensagem.setPrioridade(PrioridadeNotificacao.valueOf(input.prioridade()));
        mensagem.setStatus(StatusNotificacao.PENDENTE);
        mensagem.setCusto(BigDecimal.ZERO);
        return mensagem;
    }

    /**
     * Gera um conversaId DETERMINÍSTICO baseado no par remetente-destinatário
     * O mesmo par sempre gera o mesmo UUID, independente da ordem
     *
     * Exemplo:
     * - Cliente 1 → Empresa 2 = conversaId X
     * - Empresa 2 → Cliente 1 = conversaId X (mesmo!)
     */
    private static UUID gerarConversaId(Long remetenteId, Long destinatarioId) {
        // Ordena IDs para garantir mesmo hash independente da direção
        long menor = Math.min(remetenteId, destinatarioId);
        long maior = Math.max(remetenteId, destinatarioId);

        // Cria string única para o par
        String chave = menor + "-" + maior;

        try {
            // Gera hash SHA-256 da chave
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(chave.getBytes(StandardCharsets.UTF_8));

            // Usa os primeiros 16 bytes do hash para criar UUID
            long msb = 0;
            long lsb = 0;
            for (int i = 0; i < 8; i++) {
                msb = (msb << 8) | (hash[i] & 0xff);
            }
            for (int i = 8; i < 16; i++) {
                lsb = (lsb << 8) | (hash[i] & 0xff);
            }

            return new UUID(msb, lsb);

        } catch (Exception e) {
            // Fallback: gera UUID aleatório (não deveria acontecer)
            return UUID.randomUUID();
        }
    }
}

