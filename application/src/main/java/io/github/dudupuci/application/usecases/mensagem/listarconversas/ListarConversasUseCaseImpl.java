package io.github.dudupuci.application.usecases.mensagem.listarconversas;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.entities.Empresa;
import io.github.dudupuci.domain.entities.Mensagem;
import io.github.dudupuci.domain.enums.StatusNotificacao;
import io.github.dudupuci.domain.enums.TipoUsuario;
import io.github.dudupuci.domain.repositories.ClienteRepository;
import io.github.dudupuci.domain.repositories.EmpresaRepository;
import io.github.dudupuci.domain.repositories.MensagemRepository;

import java.util.*;

/**
 * Implementação do caso de uso para listar conversas do usuário
 */
public class ListarConversasUseCaseImpl extends ListarConversasUseCase {

    private final MensagemRepository mensagemRepository;
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;

    public ListarConversasUseCaseImpl(
            MensagemRepository mensagemRepository,
            ClienteRepository clienteRepository,
            EmpresaRepository empresaRepository
    ) {
        this.mensagemRepository = mensagemRepository;
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
    }

    @Override
    public ListarConversasOutput execute(ListarConversasInput input) {
        if (input.usuarioId() == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }

        if (input.tipoUsuario() == null) {
            throw new IllegalArgumentException("Tipo do usuário não pode ser nulo");
        }

        // Busca todas as mensagens onde o usuário é remetente ou destinatário
        List<Mensagem> mensagensRecebidas = mensagemRepository.buscarPorDestinatarioId(input.usuarioId());
        List<Mensagem> mensagensEnviadas = mensagemRepository.buscarPorRemetenteId(input.usuarioId());

        // Agrupa mensagens por conversaId
        Map<UUID, List<Mensagem>> mensagensPorConversa = new HashMap<>();

        for (Mensagem msg : mensagensRecebidas) {
            mensagensPorConversa.computeIfAbsent(msg.getConversaId(), k -> new ArrayList<>()).add(msg);
        }

        for (Mensagem msg : mensagensEnviadas) {
            mensagensPorConversa.computeIfAbsent(msg.getConversaId(), k -> new ArrayList<>()).add(msg);
        }

        // Cria DTOs de conversas
        List<ConversaDto> conversas = mensagensPorConversa.entrySet().stream()
                .map(entry -> criarConversaDto(entry.getKey(), entry.getValue(), input.usuarioId()))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(ConversaDto::ultimaMensagemDataHora,
                        Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .toList();

        return new ListarConversasOutput(conversas, conversas.size());
    }

    private ConversaDto criarConversaDto(UUID conversaId, List<Mensagem> mensagens, Long usuarioLogadoId) {
        if (mensagens.isEmpty()) {
            return null;
        }

        // Filtra mensagens com momentoEnvio não nulo e ordena por data (mais recente primeiro)
        List<Mensagem> mensagensValidas = mensagens.stream()
                .filter(m -> m.getMomentoEnvio() != null)
                .sorted(Comparator.comparing(Mensagem::getMomentoEnvio).reversed())
                .toList();

        if (mensagensValidas.isEmpty()) {
            return null;
        }

        Mensagem ultimaMensagem = mensagensValidas.getFirst();

        // Identifica o outro usuário da conversa
        Long outroUsuarioId = ultimaMensagem.getRemetenteId().equals(usuarioLogadoId)
                ? ultimaMensagem.getDestinatarioId()
                : ultimaMensagem.getRemetenteId();

        // Conta mensagens não lidas (recebidas e com status PENDENTE)
        int naoLidas = (int) mensagensValidas.stream()
                .filter(m -> m.getDestinatarioId().equals(usuarioLogadoId))
                .filter(m -> m.getStatus() == StatusNotificacao.PENDENTE)
                .count();

        // Busca informações do outro usuário e determina o tipo
        String nomeOutroUsuario = "Usuário Desconhecido";
        TipoUsuario tipoOutroUsuario;

        // Tenta buscar como cliente primeiro
        Optional<Cliente> clienteOpt = clienteRepository.buscarPorId(outroUsuarioId);
        if (clienteOpt.isPresent()) {
            nomeOutroUsuario = clienteOpt.get().getNome();
            tipoOutroUsuario = TipoUsuario.CLIENTE;
        } else {
            // Se não é cliente, tenta como empresa
            Optional<Empresa> empresaOpt = empresaRepository.buscarPorId(outroUsuarioId);
            if (empresaOpt.isPresent()) {
                nomeOutroUsuario = empresaOpt.get().getRazaoSocial();
                tipoOutroUsuario = TipoUsuario.EMPRESA;
            } else {
                // Fallback caso não encontre (não deveria acontecer)
                tipoOutroUsuario = TipoUsuario.CLIENTE;
            }
        }

        // Garante que o conteúdo não seja nulo
        String conteudoUltimaMensagem = ultimaMensagem.getConteudo() != null
                ? ultimaMensagem.getConteudo()
                : "";

        return new ConversaDto(
                conversaId,
                outroUsuarioId,
                nomeOutroUsuario,
                tipoOutroUsuario,
                conteudoUltimaMensagem,
                ultimaMensagem.getMomentoEnvio(),
                naoLidas
        );
    }
}


