package io.github.dudupuci.application.usecases.mensagem.enviar;

import io.github.dudupuci.domain.entities.Mensagem;
import io.github.dudupuci.domain.enums.TipoUsuario;
import io.github.dudupuci.domain.repositories.ClienteRepository;
import io.github.dudupuci.domain.repositories.EmpresaRepository;
import io.github.dudupuci.domain.repositories.MensagemRepository;


public class EnviarMensagemUseCaseImpl extends EnviarMensagemUseCase {

    private final MensagemRepository mensagemRepository;
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    private TipoUsuario tipoRemetente;
    private TipoUsuario tipoDestinatario;

    public EnviarMensagemUseCaseImpl(MensagemRepository mensagemRepository, ClienteRepository clienteRepository, EmpresaRepository empresaRepository) {
        this.mensagemRepository = mensagemRepository;
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
    }

    @Override
    public EnviarMensagemOutput execute(EnviarMensagemInput input) {

        validarConteudo(input.conteudo());

        validarRemetenteAndDestinatario(input.remetenteId(), input.destinatarioId());

        Mensagem mensagem = EnviarMensagemInput.criarEntidade(input);

        Mensagem mensagemSalva = mensagemRepository.salvar(mensagem);

        return new EnviarMensagemOutput(
                mensagemSalva.getId(),
                mensagemSalva.getConversaId(),
                mensagemSalva.getRemetenteId(),
                mensagemSalva.getDestinatarioId(),
                mensagemSalva.getConteudo(),
                mensagemSalva.getStatus(),
                mensagemSalva.getMomentoEnvio(),
                true
        );
    }

    private void validarConteudo(String conteudo) {
        if (conteudo == null || conteudo.trim().isEmpty()) {
            throw new IllegalArgumentException("Conteúdo da mensagem não pode ser vazio");
        }
    }

    private void validarRemetenteAndDestinatario(Long remetenteId, Long destinatarioId) {
        if (remetenteId == null) {
            throw new IllegalArgumentException("Remetente não pode ser nulo");
        }

        if (destinatarioId == null) {
            throw new IllegalArgumentException("Destinatário não pode ser nulo");
        }

        if (!remetenteId.equals(destinatarioId)) {
            return;
        }

        // IDs iguais - precisa verificar se são do mesmo tipo
        boolean remetenteCliente = clienteRepository.buscarPorId(remetenteId).isPresent();
        boolean destinatarioCliente = clienteRepository.buscarPorId(destinatarioId).isPresent();

        if (remetenteCliente && destinatarioCliente) {
            throw new IllegalArgumentException("Remetente e destinatário não podem ser a mesma pessoa");
        }

        boolean remetenteEmpresa = empresaRepository.buscarPorId(remetenteId).isPresent();
        boolean destinatarioEmpresa = empresaRepository.buscarPorId(destinatarioId).isPresent();

        if (remetenteEmpresa && destinatarioEmpresa) {
            throw new IllegalArgumentException("Remetente e destinatário não podem ser a mesma pessoa");
        }
    }
}