package io.github.dudupuci.infrastructure.persistence.repository.jpa;

import io.github.dudupuci.infrastructure.persistence.ClienteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteJpaRepository extends JpaRepository<ClienteJpaEntity, Long> {

        @Query("SELECT c FROM ClienteJpaEntity c WHERE LOWER(c.email) = LOWER(:email)")
        Optional<ClienteJpaEntity> findByEmail(@Param("email") String email);

        /**
         * Busca cliente por múltiplos critérios (email, CPF/CNPJ, telefone, nome ou sobrenome)
         * @param searchTerm termo de busca
         * @return Optional com o cliente encontrado
         */
        @Query("""
                SELECT c FROM ClienteJpaEntity c
                WHERE LOWER(c.email) = LOWER(:searchTerm)
                OR c.cpfCnpj = :searchTerm
                OR c.telefone = :searchTerm
                OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
                OR LOWER(c.sobrenome) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
""")
        Optional<ClienteJpaEntity> findBySearchTerm(@Param("searchTerm") String searchTerm);
}

