package io.github.dudupuci.infrastructure.web.dtos.response.empresa;

import io.github.dudupuci.application.usecases.empresa.criar.CriarEmpresaOutput;

import java.util.UUID;

public record CriarEmpresaApiResponse(
        UUID id,
        String razaoSocial,
        Boolean sucesso
) {
    public static CriarEmpresaApiResponse toApiResponse(CriarEmpresaOutput applicationOutput) {
        return new CriarEmpresaApiResponse(
                applicationOutput.id(),
                applicationOutput.razaoSocial(),
                true
        );
    }
}

