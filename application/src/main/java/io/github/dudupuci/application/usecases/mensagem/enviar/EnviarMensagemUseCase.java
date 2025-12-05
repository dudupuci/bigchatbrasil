package io.github.dudupuci.application.usecases.mensagem.enviar;

import io.github.dudupuci.application.UseCase;
import io.github.dudupuci.domain.entities.Mensagem;
import io.github.dudupuci.domain.repositories.ClienteRepository;
import io.github.dudupuci.domain.repositories.EmpresaRepository;
import io.github.dudupuci.domain.repositories.MensagemRepository;

import java.util.Objects;

public abstract class EnviarMensagemUseCase extends UseCase<EnviarMensagemInput, EnviarMensagemOutput> {
}

