package io.github.dudupuci.infrastructure.queue;

import io.github.dudupuci.domain.entities.Mensagem;
import io.github.dudupuci.domain.enums.StatusNotificacao;
import io.github.dudupuci.domain.repositories.MensagemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Processador SÍNCRONO de mensagens com fila em memória (FIFO)
 * Requisitos do desafio:
 * - Fila em memória (array/lista)
 * - Processamento síncrono na mesma requisição
 * - Ordenação FIFO (primeiro a entrar, primeiro a sair)
 * - Registro de status das mensagens processadas
 */
@Service
public class MessageQueueProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MessageQueueProcessor.class);

    // Fila em memória (LinkedList implementa Queue - FIFO)
    private final Queue<ItemFila> fila = new ConcurrentLinkedQueue<>();

    // Histórico de mensagens processadas
    private final List<ItemFila> historico = Collections.synchronizedList(new ArrayList<>());

    // Contador para ordem sequencial
    private final AtomicInteger contadorOrdem = new AtomicInteger(0);

    private final MensagemRepository mensagemRepository;

    public MessageQueueProcessor(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    /**
     * Adiciona mensagem na fila (enfileira)
     * @param mensagem Mensagem a ser adicionada
     * @return ItemFila com metadados
     */
    public ItemFila enqueueMessage(Mensagem mensagem) {
        ItemFila item = new ItemFila(
                mensagem,
                contadorOrdem.incrementAndGet(),
                Instant.now()
        );

        fila.offer(item); // Adiciona no final da fila (FIFO)

        logger.info("Mensagem {} adicionada na fila. Posição: {}, Tamanho fila: {}",
                mensagem.getId(), item.getOrdemNaFila(), fila.size());

        return item;
    }

    /**
     * Processa a próxima mensagem da fila (FIFO - primeiro a entrar, primeiro a sair)
     * PROCESSAMENTO SÍNCRONO - executa na mesma thread/requisição
     * @return ItemFila processado ou null se fila vazia
     */
    public ItemFila processarProxima() {
        ItemFila item = fila.poll(); // Remove do início da fila (FIFO)

        if (item == null) {
            logger.debug("Fila vazia, nenhuma mensagem para processar");
            return null;
        }

        return processarItem(item);
    }

    /**
     * Adiciona na fila e processa IMEDIATAMENTE (síncrono na mesma requisição)
     * @param mensagem Mensagem a ser processada
     * @return ItemFila processado
     */
    public ItemFila processarSincrono(Mensagem mensagem) {
        // 1. Enfileira
        ItemFila item = enqueueMessage(mensagem);

        // 2. Processa IMEDIATAMENTE (síncrono)
        return processarProxima();
    }

    /**
     * Processa um item da fila (lógica de processamento)
     */
    private ItemFila processarItem(ItemFila item) {
        try {
            logger.info("Processando SÍNCRONO mensagem ID: {}, Ordem: {}",
                    item.getMensagem().getId(), item.getOrdemNaFila());

            Instant inicioProcessamento = Instant.now();

            // Marca como PROCESSANDO
            item.getMensagem().setStatus(StatusNotificacao.PROCESSANDO);

            // Simula processamento/envio da mensagem
            // Em produção, aqui você faria integração com serviço externo, etc

            // Marca como ENVIADA
            item.getMensagem().setStatus(StatusNotificacao.ENVIADA);
            item.getMensagem().setMomentoEnvio(Instant.now());
            item.getMensagem().setDataAtualizacao(Instant.now());

            // Atualiza no banco
            mensagemRepository.atualizar(item.getMensagem());

            // Marca item como processado
            item.marcarComoProcessada(Instant.now());

            // Adiciona ao histórico
            historico.add(item);

            long tempoMs = item.getTempoProcessamentoMs();
            logger.info("Mensagem {} processada com SUCESSO em {}ms. Status: {}",
                    item.getMensagem().getId(), tempoMs, item.getStatusProcessamento());

            return item;

        } catch (Exception e) {
            logger.error("Erro ao processar mensagem {}", item.getMensagem().getId(), e);

            // Marca como falha
            item.getMensagem().setStatus(StatusNotificacao.FALHOU);
            item.getMensagem().setDataAtualizacao(Instant.now());

            try {
                mensagemRepository.atualizar(item.getMensagem());
            } catch (Exception ex) {
                logger.error("Erro ao atualizar status de falha", ex);
            }

            item.marcarComoErro(e.getMessage(), Instant.now());
            historico.add(item);

            return item;
        }
    }

    /**
     * Processa todas as mensagens pendentes na fila
     * @return Lista de itens processados
     */
    public List<ItemFila> processarTodas() {
        List<ItemFila> processados = new ArrayList<>();
        ItemFila item;

        while ((item = processarProxima()) != null) {
            processados.add(item);
        }

        logger.info("Processadas {} mensagens da fila", processados.size());
        return processados;
    }

    /**
     * Retorna o tamanho atual da fila
     */
    public int tamanhoFila() {
        return fila.size();
    }

    /**
     * Verifica se a fila está vazia
     */
    public boolean filaVazia() {
        return fila.isEmpty();
    }

    /**
     * Retorna o histórico de mensagens processadas
     */
    public List<ItemFila> obterHistorico() {
        return new ArrayList<>(historico);
    }

    /**
     * Limpa o histórico de mensagens processadas
     */
    public void limparHistorico() {
        historico.clear();
        logger.info("Histórico de mensagens limpo");
    }

    /**
     * Retorna estatísticas da fila
     */
    public FilaEstatisticas obterEstatisticas() {
        long totalProcessadas = historico.size();
        long sucessos = historico.stream()
                .filter(item -> item.getStatusProcessamento() == StatusProcessamento.SUCESSO)
                .count();
        long erros = historico.stream()
                .filter(item -> item.getStatusProcessamento() == StatusProcessamento.ERRO)
                .count();
        double tempoMedio = historico.stream()
                .mapToLong(ItemFila::getTempoProcessamentoMs)
                .average()
                .orElse(0.0);

        return new FilaEstatisticas(
                fila.size(),
                (int) totalProcessadas,
                (int) sucessos,
                (int) erros,
                tempoMedio
        );
    }

    // ========== Classes Internas ==========

    /**
     * Classe que representa um item na fila com metadados de processamento
     */
    public static class ItemFila {
        private final Mensagem mensagem;
        private final int ordemNaFila;
        private final Instant adicionadoEm;
        private StatusProcessamento statusProcessamento;
        private Instant processadoEm;
        private String mensagemErro;

        public ItemFila(Mensagem mensagem, int ordemNaFila, Instant adicionadoEm) {
            this.mensagem = mensagem;
            this.ordemNaFila = ordemNaFila;
            this.adicionadoEm = adicionadoEm;
            this.statusProcessamento = StatusProcessamento.PENDENTE;
        }

        public void marcarComoProcessada(Instant processadoEm) {
            this.statusProcessamento = StatusProcessamento.SUCESSO;
            this.processadoEm = processadoEm;
        }

        public void marcarComoErro(String mensagemErro, Instant processadoEm) {
            this.statusProcessamento = StatusProcessamento.ERRO;
            this.mensagemErro = mensagemErro;
            this.processadoEm = processadoEm;
        }

        public long getTempoProcessamentoMs() {
            if (processadoEm == null) return 0;
            return processadoEm.toEpochMilli() - adicionadoEm.toEpochMilli();
        }

        public Mensagem getMensagem() { return mensagem; }
        public int getOrdemNaFila() { return ordemNaFila; }
        public Instant getAdicionadoEm() { return adicionadoEm; }
        public StatusProcessamento getStatusProcessamento() { return statusProcessamento; }
        public Instant getProcessadoEm() { return processadoEm; }
        public String getMensagemErro() { return mensagemErro; }
    }

    /**
     * Enum para status de processamento do item na fila
     */
    public enum StatusProcessamento {
        PENDENTE,
        SUCESSO,
        ERRO
    }

    /**
     * Record para estatísticas da fila
     */
    public record FilaEstatisticas(
            int mensagensPendentes,
            int totalProcessadas,
            int sucessos,
            int erros,
            double tempoMedioProcessamentoMs
    ) {}
}

