package io.github.dudupuci.infrastructure.persistence.facade.mensagens;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemInput;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemOutput;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensInput;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensOutput;

public interface MensagemFacade {
    EnviarMensagemOutput enviarMensagem(EnviarMensagemInput input);
    ListarMensagensOutput listarMensagens(ListarMensagensInput input);
}
