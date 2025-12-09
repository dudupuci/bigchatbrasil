package io.github.dudupuci.infrastructure.persistence;

import io.github.dudupuci.domain.enums.TipoConversa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

    @Column(name = "usuario1_id", nullable = false)
    private Long usuario1Id;

    @Column(name = "usuario2_id", nullable = false)
    private Long usuario2Id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoConversa tipo = TipoConversa.INDIVIDUAL;

    @Column(name = "criada_em", nullable = false)
    private Instant criadaEm;

    @Column(name = "ultima_atualizacao")
    private Instant ultimaAtualizacao;

    /**
     * Relacionamento OneToMany com mensagens
     * FetchType.LAZY: mensagens só são carregadas quando acessadas
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversa_id", referencedColumnName = "conversa_id", insertable = false, updatable = false)
    private List<MensagemJpaEntity> mensagens = new ArrayList<>();
}

