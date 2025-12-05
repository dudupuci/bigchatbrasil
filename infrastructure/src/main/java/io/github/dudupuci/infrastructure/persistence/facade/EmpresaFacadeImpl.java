package io.github.dudupuci.infrastructure.persistence.facade;

import io.github.dudupuci.application.usecases.empresa.atualizar.AtualizarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.atualizar.AtualizarEmpresaUseCase;
import io.github.dudupuci.application.usecases.empresa.buscar.BuscarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.buscar.BuscarEmpresaOutput;
import io.github.dudupuci.application.usecases.empresa.buscar.BuscarEmpresaUseCase;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaOutput;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaUseCase;
import io.github.dudupuci.application.usecases.empresa.deletar.DeletarEmpresaUseCase;
import org.springframework.stereotype.Service;

@Service
public class EmpresaFacadeImpl implements EmpresaFacade {

    private final CriarEmpresaUseCase criarEmpresaUseCase;
    private final AtualizarEmpresaUseCase atualizarEmpresaUseCase;
    private final BuscarEmpresaUseCase buscarEmpresaUseCase;
    private final DeletarEmpresaUseCase deletarEmpresaUseCase;

    public EmpresaFacadeImpl(
            CriarEmpresaUseCase criarEmpresaUseCase,
            AtualizarEmpresaUseCase atualizarEmpresaUseCase,
            BuscarEmpresaUseCase buscarEmpresaUseCase,
            DeletarEmpresaUseCase deletarEmpresaUseCase
    ) {
        this.criarEmpresaUseCase = criarEmpresaUseCase;
        this.atualizarEmpresaUseCase = atualizarEmpresaUseCase;
        this.buscarEmpresaUseCase = buscarEmpresaUseCase;
        this.deletarEmpresaUseCase = deletarEmpresaUseCase;
    }

    @Override
    public CriarEmpresaOutput criar(CriarEmpresaInput input) {
        return this.criarEmpresaUseCase.execute(input);
    }

    @Override
    public void atualizar(AtualizarEmpresaInput input) {
        this.atualizarEmpresaUseCase.execute(input);
    }

    @Override
    public BuscarEmpresaOutput buscar(Long input) {
        return this.buscarEmpresaUseCase.execute(input);
    }

    @Override
    public void deletar(Long input) { this.deletarEmpresaUseCase.execute(input);}
}