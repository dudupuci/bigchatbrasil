package io.github.dudupuci.application.usecases.empresa.criar;

public record CriarEmpresaOutput(
        Long id,
        String razaoSocial,
        Boolean sucesso
) {
}

