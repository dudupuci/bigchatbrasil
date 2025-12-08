package io.github.dudupuci.infrastructure.persistence.facade.mensagens;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemInput;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemOutput;

public interface MensagemFacade {
    EnviarMensagemOutput enviarMensagem(EnviarMensagemInput input);
}
