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
        System.out.println("🔍 DEBUG CriarConversa:");
        System.out.println("   → UsuarioId1: " + input.usuarioId1() + " (tipo: " + input.tipoUsuario1() + ")");
        System.out.println("   → UsuarioId2: " + input.usuarioId2() + " (tipo: " + input.tipoUsuario2() + ")");

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

        System.out.println("   → Mesmo ID? " + mesmoId);
        System.out.println("   → Mesmo Tipo? " + mesmoTipo);

        if (mesmoId && mesmoTipo) {
            throw new IllegalArgumentException("Não é possível criar conversa consigo mesmo");
        }

        // Gera conversaId determinístico (AGORA CONSIDERA O TIPO!)
        UUID conversaId = gerarConversaId(input);
        System.out.println("   → ConversaId gerado: " + conversaId);

        // Verifica se conversa já existe
        if (!conversaRepository.existePorId(conversaId)) {
            System.out.println("   → Conversa não existe, criando...");

            // Determina qual usuário será usuario1 e usuario2 (ordem alfabética do tipo+id)
            String key1 = input.tipoUsuario1() + "-" + input.usuarioId1();
            String key2 = input.tipoUsuario2() + "-" + input.usuarioId2();

            // Cria a conversa no banco
            Conversa conversa = new Conversa();
            conversa.setConversaId(conversaId);

            // Define usuários em ordem consistente
            if (key1.compareTo(key2) < 0) {
                conversa.setUsuario1Id(input.usuarioId1());
                conversa.setUsuario1Tipo(input.tipoUsuario1());
                conversa.setUsuario2Id(input.usuarioId2());
                conversa.setUsuario2Tipo(input.tipoUsuario2());
            } else {
                conversa.setUsuario1Id(input.usuarioId2());
                conversa.setUsuario1Tipo(input.tipoUsuario2());
                conversa.setUsuario2Id(input.usuarioId1());
                conversa.setUsuario2Tipo(input.tipoUsuario1());
            }

            conversa.setTipo(TipoConversa.INDIVIDUAL);
            conversa.setCriadaEm(Instant.now());
            conversa.setUltimaAtualizacao(Instant.now());

            System.out.println("   → Salvando - Usuario1: " + conversa.getUsuario1Id() + " (" + conversa.getUsuario1Tipo() + ")");
            System.out.println("   → Salvando - Usuario2: " + conversa.getUsuario2Id() + " (" + conversa.getUsuario2Tipo() + ")");

            conversaRepository.salvar(conversa);
            System.out.println("   → ✅ Conversa salva no banco!");
        } else {
            System.out.println("   → ⚠️ Conversa já existe, retornando ID existente");
        }

        return new CriarConversaOutput(conversaId);
    }

    // ...existing code...

    /**
     * Gera um conversaId DETERMINÍSTICO baseado no par de usuários
     * O mesmo par sempre gera o mesmo UUID, independente da ordem
     * IMPORTANTE: Considera ID + TIPO para diferenciar Cliente e Empresa com mesmo ID
     */
    private UUID gerarConversaId(CriarConversaInput input) {
        // NÃO PODE usar apenas IDs! Cliente ID 1 e Empresa ID 1 são diferentes!
        // Precisamos incluir o TIPO no hash

        // Cria identificadores únicos: "CLIENTE-1" e "EMPRESA-2"
        String usuario1Key = input.tipoUsuario1() + "-" + input.usuarioId1();
        String usuario2Key = input.tipoUsuario2() + "-" + input.usuarioId2();

        // Ordena alfabeticamente para garantir mesmo hash independente da ordem
        String chave;
        if (usuario1Key.compareTo(usuario2Key) < 0) {
            chave = usuario1Key + "|" + usuario2Key;
        } else {
            chave = usuario2Key + "|" + usuario1Key;
        }

        System.out.println("   → Chave para hash: " + chave);

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

