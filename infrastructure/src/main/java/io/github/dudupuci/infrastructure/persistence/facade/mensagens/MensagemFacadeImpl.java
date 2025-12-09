package io.github.dudupuci.infrastructure.persistence.facade.mensagens;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemInput;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemOutput;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemUseCase;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensInput;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensOutput;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensUseCase;
import org.springframework.stereotype.Service;

@Service
public class MensagemFacadeImpl implements MensagemFacade{

    private final EnviarMensagemUseCase enviarMensagemUseCase;
    private final ListarMensagensUseCase listarMensagensUseCase;

    public MensagemFacadeImpl(
            EnviarMensagemUseCase enviarMensagemUseCase,
            ListarMensagensUseCase listarMensagensUseCase
    ) {
        this.enviarMensagemUseCase = enviarMensagemUseCase;
        this.listarMensagensUseCase = listarMensagensUseCase;
    }

    @Override
    public EnviarMensagemOutput enviarMensagem(EnviarMensagemInput input) {
        return this.enviarMensagemUseCase.execute(input);
    }

    @Override
    public ListarMensagensOutput listarMensagens(ListarMensagensInput input) {
        return this.listarMensagensUseCase.execute(input);
    }
}
