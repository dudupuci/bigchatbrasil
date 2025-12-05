package io.github.dudupuci.infrastructure.configuration;

import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClientePorParamsUseCase;
import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClientePorParamsUseCaseImpl;
import io.github.dudupuci.domain.repositories.ClienteRepository;
import io.github.dudupuci.application.usecases.cliente.atualizar.AtualizarClienteUseCase;
import io.github.dudupuci.application.usecases.cliente.atualizar.AtualizarClienteUseCaseImpl;
import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClienteUseCase;
import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClienteUseCaseImpl;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteUseCase;
import io.github.dudupuci.application.usecases.cliente.criar.CriarClienteUseCaseImpl;
import io.github.dudupuci.application.usecases.cliente.deletar.DeletarClienteUseCase;
import io.github.dudupuci.application.usecases.cliente.deletar.DeletarClienteUseCaseImpl;
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

    @Bean
    public AtualizarClienteUseCase atualizarClienteUseCase() {
        return new AtualizarClienteUseCaseImpl(repository);
    }

    @Bean
    public BuscarClienteUseCase buscarClienteUseCase() {
        return new BuscarClienteUseCaseImpl(repository);
    }

    @Bean
    public BuscarClientePorParamsUseCase buscarClientePorParamsUseCase() {
        return new BuscarClientePorParamsUseCaseImpl(repository);
    }

    @Bean
    public DeletarClienteUseCase deletarClienteUseCase() {
        return new DeletarClienteUseCaseImpl(repository);
    }

}
