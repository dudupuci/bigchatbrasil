package io.github.dudupuci.infrastructure.configuration;

import io.github.dudupuci.domain.repositories.ClienteRepository;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteUseCase;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteConfig {

    private final ClienteRepository repository;

    public ClienteConfig(ClienteRepository repository) {
        this.repository = repository;
    }

    @Bean
    public CriarClienteUseCase criarClienteUseCase() {
        return new CriarClienteUseCaseImpl(repository);
    }

}
