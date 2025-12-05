package io.github.dudupuci.infrastructure.persistence.facade.mensagens;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemInput;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemOutput;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemUseCase;
import org.springframework.stereotype.Service;

@Service
public class MensagemFacadeImpl implements MensagemFacade{

    private final EnviarMensagemUseCase enviarMensagemUseCase;

    public MensagemFacadeImpl(EnviarMensagemUseCase enviarMensagemUseCase) {
        this.enviarMensagemUseCase = enviarMensagemUseCase;
    }

    @Override
    public EnviarMensagemOutput enviarMensagem(EnviarMensagemInput input) {
        return this.enviarMensagemUseCase.execute(input);
    }
}
