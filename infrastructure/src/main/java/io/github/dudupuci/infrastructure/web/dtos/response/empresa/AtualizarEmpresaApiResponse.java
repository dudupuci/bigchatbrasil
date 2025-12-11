package io.github.dudupuci.infrastructure.web.dtos.response.empresa;

import java.util.UUID;

public record AtualizarEmpresaApiResponse(
        UUID id,
        String razaoSocial,
        Boolean sucesso
) {
    public static AtualizarEmpresaApiResponse toApiResponse(UUID id, String razaoSocial) {
        return new AtualizarEmpresaApiResponse(
                id,
                razaoSocial,
                true
        );
    }
}

