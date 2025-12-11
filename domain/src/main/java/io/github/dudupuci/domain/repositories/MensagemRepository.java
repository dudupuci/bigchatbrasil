package io.github.dudupuci.domain.repositories;

import io.github.dudupuci.domain.entities.Mensagem;
import io.github.dudupuci.domain.repositories.base.SimpleCrudInterface;

import java.util.List;
import java.util.UUID;

public interface MensagemRepository extends SimpleCrudInterface<Mensagem, UUID> {
    List<Mensagem> buscarPorConversaId(UUID conversaId);
    List<Mensagem> buscarPorDestinatarioId(UUID destinatarioId);
    List<Mensagem> buscarPorRemetenteId(UUID remetenteId);
}

