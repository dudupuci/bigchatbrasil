package io.github.dudupuci.infrastructure.web.dtos.response.empresa;

public record AtualizarEmpresaApiResponse(
        Long id,
        String razaoSocial,
        Boolean sucesso
) {
    public static AtualizarEmpresaApiResponse toApiResponse(Long id, String razaoSocial) {
        return new AtualizarEmpresaApiResponse(
                id,
                razaoSocial,
                true
        );
    }
}

