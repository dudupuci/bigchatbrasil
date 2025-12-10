package io.github.dudupuci.application.usecases.mensagem.listar;

import io.github.dudupuci.domain.entities.Mensagem;
import io.github.dudupuci.domain.repositories.MensagemRepository;

import java.util.Comparator;
import java.util.List;

/**
 * Implementação do caso de uso para listar mensagens de uma conversa
 */
public class ListarMensagensUseCaseImpl extends ListarMensagensUseCase {

    private final MensagemRepository mensagemRepository;

    public ListarMensagensUseCaseImpl(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    @Override
    public ListarMensagensOutput execute(ListarMensagensInput input) {
        if (input.conversaId() == null) {
            throw new IllegalArgumentException("ID da conversa não pode ser nulo");
        }

        if (input.usuarioId() == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }

        // Busca todas as mensagens da conversa
        List<Mensagem> mensagens = mensagemRepository.buscarPorConversaId(input.conversaId());

        // Valida que o usuário participa da conversa
        boolean participaDaConversa = mensagens.stream()
                .anyMatch(m -> m.getRemetenteId().equals(input.usuarioId())
                        || m.getDestinatarioId().equals(input.usuarioId()));

        if (!participaDaConversa && !mensagens.isEmpty()) {
            throw new IllegalArgumentException("Você não tem permissão para acessar esta conversa");
        }

        List<MensagemDto> mensagensDto = mensagens.stream()
                .filter(m -> m.getDataCriacao() != null) // Filtra mensagens com data válida
                .sorted(Comparator.comparing(Mensagem::getDataCriacao, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(m -> new MensagemDto(
                        m.getId(),
                        m.getConversaId(),
                        m.getRemetenteId(),
                        m.getDestinatarioId(),
                        m.getConteudo(),
                        m.getStatus(),
                        m.getDataCriacao()
                ))
                .toList();

        return new ListarMensagensOutput(
                input.conversaId(),
                mensagensDto,
                mensagensDto.size()
        );
    }
}

