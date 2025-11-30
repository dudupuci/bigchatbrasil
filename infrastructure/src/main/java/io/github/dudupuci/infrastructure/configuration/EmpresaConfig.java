package io.github.dudupuci.infrastructure.configuration;

import io.github.dudupuci.domain.repositories.EmpresaRepository;
import io.github.dudupuci.application.usecases.empresa.atualizar.AtualizarEmpresaUseCase;
import io.github.dudupuci.application.usecases.empresa.atualizar.AtualizarEmpresaUseCaseImpl;
import io.github.dudupuci.application.usecases.empresa.buscar.BuscarEmpresaUseCase;
import io.github.dudupuci.application.usecases.empresa.buscar.BuscarEmpresaUseCaseImpl;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaUseCase;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaUseCaseImpl;
import io.github.dudupuci.application.usecases.empresa.deletar.DeletarEmpresaUseCase;
import io.github.dudupuci.application.usecases.empresa.deletar.DeletarEmpresaUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmpresaConfig {

    private final EmpresaRepository repository;

    public EmpresaConfig(EmpresaRepository repository) {
        this.repository = repository;
    }

    @Bean
    public CriarEmpresaUseCase criarEmpresaUseCase() {
        return new CriarEmpresaUseCaseImpl(repository);
    }

    @Bean
    public AtualizarEmpresaUseCase atualizarEmpresaUseCase() {
        return new AtualizarEmpresaUseCaseImpl(repository);
    }

    @Bean
    public BuscarEmpresaUseCase buscarEmpresaUseCase() {
        return new BuscarEmpresaUseCaseImpl(repository);
    }

    @Bean
    public DeletarEmpresaUseCase deletarEmpresaUseCase() {
        return new DeletarEmpresaUseCaseImpl(repository);
    }

}

