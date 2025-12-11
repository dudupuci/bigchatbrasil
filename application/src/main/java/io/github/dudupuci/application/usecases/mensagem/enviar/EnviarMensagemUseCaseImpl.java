package io.github.dudupuci.application.usecases.mensagem.enviar;

import io.github.dudupuci.domain.constants.BcbConstants;
import io.github.dudupuci.domain.entities.Mensagem;
import io.github.dudupuci.domain.enums.TipoUsuario;
import io.github.dudupuci.domain.repositories.ClienteRepository;
import io.github.dudupuci.domain.repositories.EmpresaRepository;
import io.github.dudupuci.domain.repositories.MensagemRepository;

import java.util.UUID;


public class  EnviarMensagemUseCaseImpl extends EnviarMensagemUseCase {

    private final MensagemRepository mensagemRepository;
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;

    public EnviarMensagemUseCaseImpl(MensagemRepository mensagemRepository, ClienteRepository clienteRepository, EmpresaRepository empresaRepository) {
        this.mensagemRepository = mensagemRepository;
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
    }

    @Override
    public EnviarMensagemOutput execute(EnviarMensagemInput input) {

        validarConteudo(input.conteudo());

        validarRemetenteAndDestinatario(input);

        Mensagem mensagem = EnviarMensagemInput.criarEntidade(input);

        Mensagem mensagemSalva = mensagemRepository.salvar(mensagem);

        return new EnviarMensagemOutput(
                mensagemSalva.getId(),
                mensagemSalva.getDataCriacao(),
                mensagemSalva.getConversaId(),
                mensagemSalva.getRemetenteId(),
                mensagemSalva.getDestinatarioId(),
                mensagemSalva.getConteudo(),
                mensagemSalva.getStatus(),
                true
        );
    }

    private void validarConteudo(String conteudo) {
        if (conteudo == null || conteudo.trim().isEmpty()) {
            throw new IllegalArgumentException("Conteúdo da mensagem não pode ser vazio");
        }
    }

    /**
     * Valida que remetente e destinatário são diferentes
     * Compara ID + Tipo para evitar falso positivo (ex: Cliente#1 != Empresa#1)
     * @param input
     */
    private void validarRemetenteAndDestinatario(EnviarMensagemInput input) {
        if (input.remetenteId() == null || input.tipoRemetente() == null) {
            throw new IllegalArgumentException("Remetente/Tipo do Remetente não pode ser nulo");
        }

        if (input.destinatarioId() == null || input.tipoDestinatario() == null) {
            throw new IllegalArgumentException("Destinatário/Tipo do Destinatário não pode ser nulo");
        }

        boolean mesmoId = input.remetenteId().equals(input.destinatarioId());
        boolean mesmoTipo = input.tipoRemetente().equals(input.tipoDestinatario());

        if (mesmoId && mesmoTipo) {
            throw new IllegalArgumentException("Remetente e destinatário não podem ser a mesma pessoa");
        }

        validarExistenciaUsuario(input.remetenteId(), input.tipoRemetente(), BcbConstants.REMETENTE);
        validarExistenciaUsuario(input.destinatarioId(), input.tipoDestinatario(), BcbConstants.DESTINATARIO);
    }

    private void validarExistenciaUsuario(UUID id, TipoUsuario tipo, String papel) {
        boolean existe = switch (tipo) {
            case CLIENTE -> clienteRepository.buscarPorId(id).isPresent();
            case EMPRESA -> empresaRepository.buscarPorId(id).isPresent();
            default -> false;
        };

        if (!existe) {
            throw new IllegalArgumentException(papel + " não encontrado: " + tipo + "#" + id);
        }
    }
}