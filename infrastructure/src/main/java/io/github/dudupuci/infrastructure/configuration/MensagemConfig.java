package io.github.dudupuci.infrastructure.configuration;

import io.github.dudupuci.application.usecases.mensagem.criarconversa.CriarConversaUseCase;
import io.github.dudupuci.application.usecases.mensagem.criarconversa.CriarConversaUseCaseImpl;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemUseCase;
import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemUseCaseImpl;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensUseCase;
import io.github.dudupuci.application.usecases.mensagem.listar.ListarMensagensUseCaseImpl;
import io.github.dudupuci.application.usecases.mensagem.listarconversas.ListarConversasUseCase;
import io.github.dudupuci.application.usecases.mensagem.listarconversas.ListarConversasUseCaseImpl;
import io.github.dudupuci.domain.repositories.ClienteRepository;
import io.github.dudupuci.domain.repositories.ConversaRepository;
import io.github.dudupuci.domain.repositories.EmpresaRepository;
import io.github.dudupuci.domain.repositories.MensagemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MensagemConfig {

    private final MensagemRepository mensagemRepository;
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    private final ConversaRepository conversaRepository;

    public MensagemConfig(
            MensagemRepository mensagemRepository,
            ClienteRepository clienteRepository,
            EmpresaRepository empresaRepository,
            ConversaRepository conversaRepository
    ) {
        this.mensagemRepository = mensagemRepository;
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
        this.conversaRepository = conversaRepository;
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

    @Bean
    public ListarConversasUseCase listarConversasUseCase() {
        return new ListarConversasUseCaseImpl(
                mensagemRepository,
                clienteRepository,
                empresaRepository,
                conversaRepository
        );
    }

    @Bean
    public CriarConversaUseCase criarConversaUseCase() {
        return new CriarConversaUseCaseImpl(conversaRepository);
    }
}

