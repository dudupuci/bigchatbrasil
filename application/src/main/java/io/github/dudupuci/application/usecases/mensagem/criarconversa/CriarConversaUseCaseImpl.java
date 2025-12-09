package io.github.dudupuci.application.usecases.mensagem.criarconversa;

import io.github.dudupuci.domain.entities.Conversa;
import io.github.dudupuci.domain.enums.TipoConversa;
import io.github.dudupuci.domain.repositories.ConversaRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.UUID;

/**
 * Implementação do caso de uso para criar conversaId entre dois usuários
 */
public class CriarConversaUseCaseImpl extends CriarConversaUseCase {

    private final ConversaRepository conversaRepository;

    public CriarConversaUseCaseImpl(ConversaRepository conversaRepository) {
        this.conversaRepository = conversaRepository;
    }

    @Override
    public CriarConversaOutput execute(CriarConversaInput input) {
        // Validações
        if (input.usuarioId1() == null || input.usuarioId2() == null) {
            throw new IllegalArgumentException("IDs dos usuários não podem ser nulos");
        }

        if (input.tipoUsuario1() == null || input.tipoUsuario2() == null) {
            throw new IllegalArgumentException("Tipos dos usuários não podem ser nulos");
        }

        // Valida que não são o mesmo usuário
        boolean mesmoId = input.usuarioId1().equals(input.usuarioId2());
        boolean mesmoTipo = input.tipoUsuario1().equals(input.tipoUsuario2());

        if (mesmoId && mesmoTipo) {
            throw new IllegalArgumentException("Não é possível criar conversa consigo mesmo");
        }

        // Gera conversaId determinístico
        UUID conversaId = gerarConversaId(input.usuarioId1(), input.usuarioId2());

        // Verifica se conversa já existe
        if (!conversaRepository.existePorId(conversaId)) {
            // Cria a conversa no banco
            Conversa conversa = new Conversa();
            conversa.setConversaId(conversaId);
            conversa.setUsuario1Id(Math.min(input.usuarioId1(), input.usuarioId2()));
            conversa.setUsuario2Id(Math.max(input.usuarioId1(), input.usuarioId2()));
            conversa.setCriadaEm(Instant.now());
            conversa.setTipo(TipoConversa.INDIVIDUAL);
            conversa.setUltimaAtualizacao(Instant.now());

            conversaRepository.salvar(conversa);
        }

        return new CriarConversaOutput(conversaId);
    }

    // ...existing code...

    /**
     * Gera um conversaId DETERMINÍSTICO baseado no par de usuários
     * O mesmo par sempre gera o mesmo UUID, independente da ordem
     */
    private UUID gerarConversaId(Long usuarioId1, Long usuarioId2) {
        // Ordena IDs para garantir mesmo hash independente da direção
        long menor = Math.min(usuarioId1, usuarioId2);
        long maior = Math.max(usuarioId1, usuarioId2);

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

