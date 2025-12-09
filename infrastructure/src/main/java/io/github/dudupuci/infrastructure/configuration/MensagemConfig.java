package io.github.dudupuci.infrastructure.configuration;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemUseCase;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemUseCaseImpl;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensUseCase;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensUseCaseImpl;
import io.github.dudupuci.domain.repositories.ClienteRepository;
import io.github.dudupuci.domain.repositories.EmpresaRepository;
import io.github.dudupuci.domain.repositories.MensagemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MensagemConfig {

    private final MensagemRepository mensagemRepository;
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;

    public MensagemConfig(
            MensagemRepository mensagemRepository,
            ClienteRepository clienteRepository,
            EmpresaRepository empresaRepository
    ) {
        this.mensagemRepository = mensagemRepository;
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
    }

    @Bean
    public EnviarMensagemUseCase criarMensagemUseCase() {
        return new EnviarMensagemUseCaseImpl(
                mensagemRepository,
                clienteRepository,
                empresaRepository
        );
    }

    @Bean
    public ListarMensagensUseCase listarMensagensUseCase() {
        return new ListarMensagensUseCaseImpl(mensagemRepository);
    }
}

