package io.github.dudupuci.infrastructure.web.dtos.response.usuario;

import io.github.dudupuci.domain.enums.TipoUsuario;

import java.util.UUID;

/**
 * Response da API para busca de usuário por email
 */
public record BuscarUsuarioPorEmailApiResponse(
        UUID id,
        String nome,
        String email,
        TipoUsuario tipo
) {
}

