package io.github.dudupuci.domain.repositories.base;

import java.util.Optional;

public interface SimpleCrudInterface<Entity, ID> {
    Entity salvar(Entity entity);
    void atualizar(Entity entity);
    Optional<Entity> buscarPorId(ID id);
    void deletarPorId(ID id);
}
