package io.github.dudupuci.infrastructure.persistence.facade.mensagens;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemInput;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemOutput;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemUseCase;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensInput;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensOutput;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensUseCase;
import io.github.dudupuci.application.usecases.mensagem.listarconversas.ListarConversasInput;
import io.github.dudupuci.application.usecases.mensagem.listarconversas.ListarConversasOutput;
import io.github.dudupuci.application.usecases.mensagem.listarconversas.ListarConversasUseCase;
import org.springframework.stereotype.Service;

@Service
public class MensagemFacadeImpl implements MensagemFacade{

    private final EnviarMensagemUseCase enviarMensagemUseCase;
    private final ListarMensagensUseCase listarMensagensUseCase;
    private final ListarConversasUseCase listarConversasUseCase;

    public MensagemFacadeImpl(
            EnviarMensagemUseCase enviarMensagemUseCase,
            ListarMensagensUseCase listarMensagensUseCase,
            ListarConversasUseCase listarConversasUseCase
    ) {
        this.enviarMensagemUseCase = enviarMensagemUseCase;
        this.listarMensagensUseCase = listarMensagensUseCase;
        this.listarConversasUseCase = listarConversasUseCase;
    }

    @Override
    public EnviarMensagemOutput enviarMensagem(EnviarMensagemInput input) {
        return this.enviarMensagemUseCase.execute(input);
    }

    @Override
    public ListarMensagensOutput listarMensagens(ListarMensagensInput input) {
        return this.listarMensagensUseCase.execute(input);
    }

    @Override
    public ListarConversasOutput listarConversas(ListarConversasInput input) {
        return this.listarConversasUseCase.execute(input);
    }
}
