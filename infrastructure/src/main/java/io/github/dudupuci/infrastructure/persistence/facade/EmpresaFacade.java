package io.github.dudupuci.infrastructure.persistence.facade;

import io.github.dudupuci.application.usecases.empresa.atualizar.AtualizarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.buscar.BuscarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.buscar.BuscarEmpresaOutput;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaInput;
import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaOutput;

public interface EmpresaFacade {
    CriarEmpresaOutput criar(CriarEmpresaInput input);
    void atualizar(AtualizarEmpresaInput input);
    BuscarEmpresaOutput buscar(BuscarEmpresaInput input);
    void deletar(Long input);
}

