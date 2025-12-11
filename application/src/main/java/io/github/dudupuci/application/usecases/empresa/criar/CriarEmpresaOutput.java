package io.github.dudupuci.application.usecases.empresa.criar;

import java.util.UUID;

public record CriarEmpresaOutput(
        UUID id,
        String razaoSocial,
        Boolean sucesso
) {
}

