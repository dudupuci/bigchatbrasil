package io.github.dudupuci.infrastructure.queue;

import io.github.dudupuci.application.usecases.mensagem.enviar.EnviarMensagemInput;
import io.github.dudupuci.domain.enums.PrioridadeNotificacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Fila de mensagens em memória com suporte a priorização
 * Parte 1: FIFO básico
 * Parte 2: Fila com priorização (Normal/Urgente) com anti-starvation
 */
@Component
public class FilaMensagens {

    private static final Logger logger = LoggerFactory.getLogger(FilaMensagens.class);

    // Fila com priorização automática (mensagens urgentes primeiro)
    private final PriorityBlockingQueue<ItemFila> fila;

    // Estatísticas
    private final AtomicLong totalEnfileiradas = new AtomicLong(0);
    private final AtomicLong totalProcessadas = new AtomicLong(0);
    private final AtomicLong totalFalhas = new AtomicLong(0);

    // Mapa para rastrear status das mensagens
    private final Map<UUID, StatusMensagemFila> statusMensagens;

    // Contador para anti-starvation (evita que mensagens normais esperem indefinidamente)
    private int contadorAntiStarvation = 0;
    private static final int LIMITE_ANTI_STARVATION = 3; // Processa 1 normal a cada 3 urgentes

    public FilaMensagens() {
        // Comparator personalizado para priorização com anti-starvation
        this.fila = new PriorityBlockingQueue<>(100, (m1, m2) -> {
            // Se ambas têm mesma prioridade, FIFO (ordem de chegada)
            if (m1.prioridade == m2.prioridade) {
                return Long.compare(m1.ordemChegada, m2.ordemChegada);
            }
            // Urgentes primeiro (NORMAL=0, URGENTE=1, então inverte)
            return Integer.compare(m2.prioridade.ordinal(), m1.prioridade.ordinal());
        });
        this.statusMensagens = new ConcurrentHashMap<>();
    }

    /**
     * Adiciona mensagem na fila com priorização
     */
    public UUID enfileirar(EnviarMensagemInput input) {
        UUID idFila = UUID.randomUUID();
        long ordemChegada = totalEnfileiradas.incrementAndGet();

        ItemFila item = new ItemFila(
                idFila,
                input,
                PrioridadeNotificacao.valueOf(input.prioridade()),
                ordemChegada,
                Instant.now()
        );

        fila.offer(item);

        // Registra status
        statusMensagens.put(idFila, new StatusMensagemFila(
                idFila,
                StatusProcessamento.ENFILEIRADA,
                Instant.now(),
                null,
                null
        ));

        logger.info("Mensagem enfileirada: {} | Prioridade: {} | Fila atual: {}",
                idFila, item.prioridade, fila.size());

        return idFila;
    }

    /**
     * Remove e retorna a próxima mensagem a ser processada
     * Implementa anti-starvation: a cada 3 urgentes, processa 1 normal
     */
    public ItemFila desenfileirar() {
        if (fila.isEmpty()) {
            return null;
        }

        // Anti-starvation: a cada 3 urgentes, força processar 1 normal se existir
        contadorAntiStarvation++;
        if (contadorAntiStarvation >= LIMITE_ANTI_STARVATION) {
            contadorAntiStarvation = 0;
            ItemFila normal = buscarPrimeiraNormal();
            if (normal != null) {
                logger.debug("Anti-starvation: processando mensagem NORMAL");
                return normal;
            }
        }

        return fila.poll();
    }

    /**
     * Busca e remove a primeira mensagem NORMAL da fila (para anti-starvation)
     */
    private ItemFila buscarPrimeiraNormal() {
        List<ItemFila> temp = new ArrayList<>();
        ItemFila found = null;

        while (!fila.isEmpty()) {
            ItemFila item = fila.poll();
            if (item.prioridade == PrioridadeNotificacao.NENHUMA && found == null) {
                found = item;
            } else {
                temp.add(item);
            }
        }

        // Recoloca os itens na fila
        fila.addAll(temp);

        return found;
    }

    /**
     * Verifica se a fila está vazia
     */
    public boolean vazia() {
        return fila.isEmpty();
    }

    /**
     * Retorna o tamanho atual da fila
     */
    public int tamanho() {
        return fila.size();
    }

    /**
     * Marca mensagem como processada com sucesso
     */
    public void marcarProcessada(UUID idFila, Object resultado) {
        totalProcessadas.incrementAndGet();
        StatusMensagemFila status = statusMensagens.get(idFila);
        if (status != null) {
            status.status = StatusProcessamento.PROCESSADA;
            status.momentoProcessamento = Instant.now();
            status.resultado = resultado;
        }
    }

    /**
     * Marca mensagem como falha
     */
    public void marcarFalha(UUID idFila, String erro) {
        totalFalhas.incrementAndGet();
        StatusMensagemFila status = statusMensagens.get(idFila);
        if (status != null) {
            status.status = StatusProcessamento.FALHA;
            status.momentoProcessamento = Instant.now();
            status.erro = erro;
        }
    }

    /**
     * Busca status de uma mensagem
     */
    public StatusMensagemFila buscarStatus(UUID idFila) {
        return statusMensagens.get(idFila);
    }

    /**
     * Retorna estatísticas da fila
     */
    public EstatisticasFila obterEstatisticas() {
        long urgentes = fila.stream()
                .filter(m -> m.prioridade == PrioridadeNotificacao.ALTA)
                .count();
        long normais = fila.size() - urgentes;

        return new EstatisticasFila(
                fila.size(),
                urgentes,
                normais,
                totalEnfileiradas.get(),
                totalProcessadas.get(),
                totalFalhas.get(),
                statusMensagens.size()
        );
    }

    /**
     * Limpa mensagens antigas do status (evita memory leak)
     */
    public void limparStatusAntigos(int minutos) {
        Instant limite = Instant.now().minusSeconds(minutos * 60L);
        statusMensagens.entrySet().removeIf(entry ->
                entry.getValue().momentoEnfileiramento.isBefore(limite)
        );
    }

    // ========== Classes Internas ==========

    /**
     * Item da fila com priorização
     */
    public static class ItemFila {
        public final UUID id;
        public final EnviarMensagemInput input;
        public final PrioridadeNotificacao prioridade;
        public final long ordemChegada;
        public final Instant momentoEnfileiramento;

        public ItemFila(UUID id, EnviarMensagemInput input, PrioridadeNotificacao prioridade,
                        long ordemChegada, Instant momentoEnfileiramento) {
            this.id = id;
            this.input = input;
            this.prioridade = prioridade;
            this.ordemChegada = ordemChegada;
            this.momentoEnfileiramento = momentoEnfileiramento;
        }
    }

    /**
     * Status de processamento
     */
    public enum StatusProcessamento {
        ENFILEIRADA,
        PROCESSADA,
        FALHA
    }

    /**
     * Status de uma mensagem na fila
     */
    public static class StatusMensagemFila {
        public UUID id;
        public StatusProcessamento status;
        public Instant momentoEnfileiramento;
        public Instant momentoProcessamento;
        public Object resultado;
        public String erro;

        public StatusMensagemFila(UUID id, StatusProcessamento status,
                                   Instant momentoEnfileiramento,
                                   Instant momentoProcessamento,
                                   String erro) {
            this.id = id;
            this.status = status;
            this.momentoEnfileiramento = momentoEnfileiramento;
            this.momentoProcessamento = momentoProcessamento;
            this.erro = erro;
        }
    }

    /**
     * Estatísticas da fila
     */
    public record EstatisticasFila(
            int tamanhoAtual,
            long mensagensUrgentes,
            long mensagensNormais,
            long totalEnfileiradas,
            long totalProcessadas,
            long totalFalhas,
            int statusRastreados
    ) {}
}

