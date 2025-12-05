package io.github.dudupuci.infrastructure.messaging;

import io.github.dudupuci.domain.entities.Mensagem;
import io.github.dudupuci.domain.enums.StatusNotificacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Gerenciador de fila de mensagens em memória
 * Implementa FIFO (First In, First Out) síncrono
 */
@Slf4j
@Component
public class MessageQueueManager {

    // Fila em memória usando LinkedList para FIFO eficiente
    private final Queue<Mensagem> messageQueue = new LinkedList<>();

    // Lock para garantir thread-safety
    private final ReentrantLock lock = new ReentrantLock();

    // Histórico de mensagens processadas
    private final List<MensagemProcessada> historicoProcessamento = new ArrayList<>();

    /**
     * Adiciona uma mensagem na fila
     */
    public void enfileirar(Mensagem mensagem) {
        lock.lock();
        try {
            mensagem.setStatus(StatusNotificacao.QUEUEADA);
            messageQueue.offer(mensagem);
            log.info("Mensagem enfileirada: ID={}, Remetente={}, Destinatario={}",
                    mensagem.getId(), mensagem.getRemetenteId(), mensagem.getDestinatarioId());
        } finally {
            lock.unlock();
        }
    }

    /**
     * Remove e retorna a próxima mensagem da fila (FIFO)
     */
    public Mensagem desenfileirar() {
        lock.lock();
        try {
            return messageQueue.poll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Processa todas as mensagens da fila de forma síncrona
     * Retorna lista de mensagens processadas
     */
    public List<MensagemProcessada> processarFila() {
        lock.lock();
        List<MensagemProcessada> processadas = new ArrayList<>();

        try {
            log.info("Iniciando processamento de {} mensagens na fila", messageQueue.size());

            while (!messageQueue.isEmpty()) {
                Mensagem mensagem = messageQueue.poll();
                MensagemProcessada resultado = processar(mensagem);
                processadas.add(resultado);
                historicoProcessamento.add(resultado);
            }

            log.info("Processamento concluído: {} mensagens processadas", processadas.size());
            return processadas;

        } finally {
            lock.unlock();
        }
    }

    /**
     * Processa uma única mensagem
     */
    private MensagemProcessada processar(Mensagem mensagem) {
        Instant inicioProcessamento = Instant.now();

        try {
            log.debug("Processando mensagem ID={}", mensagem.getId());

            // Marca como processando
            mensagem.setStatus(StatusNotificacao.PROCESSANDO);

            // Simula processamento (aqui você pode adicionar lógica de negócio)
            // Por exemplo: validações, transformações, notificações, etc.

            // Marca como enviada
            mensagem.setStatus(StatusNotificacao.ENVIADA);
            mensagem.setMomentoEnvio(Instant.now());

            Instant fimProcessamento = Instant.now();
            long tempoProcessamento = fimProcessamento.toEpochMilli() - inicioProcessamento.toEpochMilli();

            log.info("Mensagem ID={} processada com sucesso em {}ms", mensagem.getId(), tempoProcessamento);

            return new MensagemProcessada(
                    mensagem,
                    true,
                    null,
                    inicioProcessamento,
                    fimProcessamento,
                    tempoProcessamento
            );

        } catch (Exception e) {
            log.error("Erro ao processar mensagem ID={}: {}", mensagem.getId(), e.getMessage(), e);

            mensagem.setStatus(StatusNotificacao.FALHOU);
            Instant fimProcessamento = Instant.now();
            long tempoProcessamento = fimProcessamento.toEpochMilli() - inicioProcessamento.toEpochMilli();

            return new MensagemProcessada(
                    mensagem,
                    false,
                    e.getMessage(),
                    inicioProcessamento,
                    fimProcessamento,
                    tempoProcessamento
            );
        }
    }

    /**
     * Retorna o tamanho atual da fila
     */
    public int tamanhoFila() {
        lock.lock();
        try {
            return messageQueue.size();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Retorna o histórico de processamento
     */
    public List<MensagemProcessada> obterHistorico() {
        lock.lock();
        try {
            return new ArrayList<>(historicoProcessamento);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Limpa o histórico de processamento
     */
    public void limparHistorico() {
        lock.lock();
        try {
            historicoProcessamento.clear();
            log.info("Histórico de processamento limpo");
        } finally {
            lock.unlock();
        }
    }

    /**
     * Verifica se a fila está vazia
     */
    public boolean estaVazia() {
        lock.lock();
        try {
            return messageQueue.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Classe interna para representar resultado do processamento
     */
    public record MensagemProcessada(
            Mensagem mensagem,
            boolean sucesso,
            String mensagemErro,
            Instant inicioProcessamento,
            Instant fimProcessamento,
            long tempoProcessamentoMs
    ) {}
}

