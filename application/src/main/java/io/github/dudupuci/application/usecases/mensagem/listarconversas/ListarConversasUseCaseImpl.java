package io.github.dudupuci.application.usecases.mensagem.listarconversas;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.entities.Conversa;
import io.github.dudupuci.domain.entities.Empresa;
import io.github.dudupuci.domain.entities.Mensagem;
import io.github.dudupuci.domain.enums.StatusNotificacao;
import io.github.dudupuci.domain.enums.TipoUsuario;
import io.github.dudupuci.domain.repositories.ClienteRepository;
import io.github.dudupuci.domain.repositories.ConversaRepository;
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
    private final ConversaRepository conversaRepository;

    public ListarConversasUseCaseImpl(
            MensagemRepository mensagemRepository,
            ClienteRepository clienteRepository,
            EmpresaRepository empresaRepository,
            ConversaRepository conversaRepository
    ) {
        this.mensagemRepository = mensagemRepository;
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
        this.conversaRepository = conversaRepository;
    }

    @Override
    public ListarConversasOutput execute(ListarConversasInput input) {
        if (input.usuarioId() == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }

        if (input.tipoUsuario() == null) {
            throw new IllegalArgumentException("Tipo do usuário não pode ser nulo");
        }

        // Busca todas as conversas do usuário da tabela conversas
        List<Conversa> conversas = conversaRepository.buscarPorUsuarioId(input.usuarioId());

        // Cria DTOs de conversas
        List<ConversaDto> conversasDto = conversas.stream()
                .map(conversa -> criarConversaDto(conversa, input.usuarioId()))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(ConversaDto::ultimaMensagemDataHora,
                        Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .toList();

        return new ListarConversasOutput(conversasDto, conversasDto.size());
    }

    private ConversaDto criarConversaDto(Conversa conversa, Long usuarioLogadoId) {
        // Identifica o outro usuário da conversa
        Long outroUsuarioId = conversa.getUsuario1Id().equals(usuarioLogadoId)
                ? conversa.getUsuario2Id()
                : conversa.getUsuario1Id();

        // Busca mensagens da conversa para pegar última mensagem e contar não lidas
        List<Mensagem> mensagens = mensagemRepository.buscarPorConversaId(conversa.getConversaId());

        // Filtra mensagens válidas e ordena
        List<Mensagem> mensagensValidas = mensagens.stream()
                .filter(m -> m.getMomentoEnvio() != null)
                .sorted(Comparator.comparing(Mensagem::getMomentoEnvio).reversed())
                .toList();

        // Pega última mensagem ou valores padrão
        String ultimaMensagemConteudo = mensagensValidas.isEmpty()
                ? "Nenhuma mensagem ainda"
                : (mensagensValidas.getFirst().getConteudo() != null
                ? mensagensValidas.getFirst().getConteudo()
                : "");

        // Conta mensagens não lidas
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
                // Fallback
                tipoOutroUsuario = TipoUsuario.CLIENTE;
            }
        }

        return new ConversaDto(
                conversa.getConversaId(),
                outroUsuarioId,
                nomeOutroUsuario,
                tipoOutroUsuario,
                ultimaMensagemConteudo,
                mensagensValidas.isEmpty() ? conversa.getCriadaEm() : mensagensValidas.getFirst().getMomentoEnvio(),
                naoLidas
        );
    }
}


