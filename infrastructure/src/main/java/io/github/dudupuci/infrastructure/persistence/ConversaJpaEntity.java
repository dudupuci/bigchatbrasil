package io.github.dudupuci.infrastructure.persistence;

import io.github.dudupuci.domain.enums.TipoConversa;
import io.github.dudupuci.domain.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * JPA Entity para Conversa
 */
@Entity
@Table(name = "conversas")
@Getter
@Setter
@NoArgsConstructor
public class ConversaJpaEntity {

    @Id
    @Column(name = "conversa_id", columnDefinition = "uuid")
    private UUID conversaId;

    @Column(name = "usuario1_id", nullable = false, columnDefinition = "uuid")
    private UUID usuario1Id;

    @Enumerated(EnumType.STRING)
    @Column(name = "usuario1_tipo", nullable = false)
    private TipoUsuario usuario1Tipo;

    @Column(name = "usuario2_id", nullable = false, columnDefinition = "uuid")
    private UUID usuario2Id;

    @Enumerated(EnumType.STRING)
    @Column(name = "usuario2_tipo", nullable = false)
    private TipoUsuario usuario2Tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoConversa tipo = TipoConversa.INDIVIDUAL;

    @Column(name = "criada_em", nullable = false)
    private Instant criadaEm;

    @Column(name = "ultima_atualizacao")
    private Instant ultimaAtualizacao;
}

