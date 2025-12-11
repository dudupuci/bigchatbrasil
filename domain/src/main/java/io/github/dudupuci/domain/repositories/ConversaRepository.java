package io.github.dudupuci.domain.repositories;

import io.github.dudupuci.domain.entities.Conversa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para Conversa
 */
public interface ConversaRepository {
    Conversa salvar(Conversa conversa);
    Optional<Conversa> buscarPorId(UUID conversaId);
    List<Conversa> buscarPorUsuarioId(UUID usuarioId);
    boolean existePorId(UUID conversaId);
}

