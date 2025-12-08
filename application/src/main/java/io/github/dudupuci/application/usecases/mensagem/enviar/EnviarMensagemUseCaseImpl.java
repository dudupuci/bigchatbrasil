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

        validarRemetente(input.remetenteId());

        validarDestinatario(input.destinatarioId());

        validarRemetenteDestinatarioDiferentes(input.remetenteId(), input.destinatarioId());

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

    private void validarRemetente(Long remetenteId) {
        if (remetenteId == null) {
            throw new IllegalArgumentException("Remetente não pode ser nulo");
        }

        boolean remetenteExiste = clienteRepository.buscarPorId(remetenteId).isPresent() ||
                empresaRepository.buscarPorId(remetenteId).isPresent();

        if (!remetenteExiste) {
            throw new IllegalArgumentException("Remetente com ID " + remetenteId + " não encontrado");
        }

    }

    private void validarDestinatario(Long destinatarioId) {
        if (destinatarioId == null) {
            throw new IllegalArgumentException("Destinatário não pode ser nulo");
        }

        boolean destinatarioExiste = clienteRepository.buscarPorId(destinatarioId).isPresent() ||
                empresaRepository.buscarPorId(destinatarioId).isPresent();

        if (!destinatarioExiste) {
            throw new IllegalArgumentException("Destinatário com ID " + destinatarioId + " não encontrado");
        }
    }

    private void validarRemetenteDestinatarioDiferentes(Long remetenteId, Long destinatarioId) {
        if (remetenteId.equals(destinatarioId)) {
            throw new IllegalArgumentException("Remetente e destinatário não podem ser a mesma pessoa");
        }
    }
}