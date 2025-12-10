package io.github.dudupuci.infrastructure.persistence;

import io.github.dudupuci.domain.enums.PrioridadeNotificacao;
import io.github.dudupuci.domain.enums.StatusNotificacao;
import io.github.dudupuci.domain.enums.TipoNotificacao;
import io.github.dudupuci.infrastructure.persistence.base.JpaEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "mensagens")
@Getter
@Setter
@NoArgsConstructor
public class MensagemJpaEntity extends JpaEntity {

    @Column(name = "conversa_id", nullable = false)
    @NotNull(message = "ID da conversa é obrigatório")
    private UUID conversaId;

    @Column(name = "remetente_id", nullable = false)
    @NotNull(message = "ID do remetente é obrigatório")
    private Long remetenteId;

    @Column(name = "destinatario_id", nullable = false)
    @NotNull(message = "ID do destinatário é obrigatório")
    private Long destinatarioId;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(min = 1)
    @NotBlank(message = "Conteúdo é obrigatório")
    private String conteudo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacao tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusNotificacao status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadeNotificacao prioridade;

    @Column(nullable = false)
    private BigDecimal custo;
}

