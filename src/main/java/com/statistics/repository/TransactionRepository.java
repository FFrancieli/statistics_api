package com.statistics.repository;

import com.statistics.models.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {

    private ConcurrentHashMap<Timestamp, Double> transactions;

    public TransactionRepository() {
        transactions = new ConcurrentHashMap();
    }

    public TransactionRepository(ConcurrentHashMap transactions) {
        this.transactions = transactions;
    }

    public ConcurrentHashMap<Timestamp, Double> save(Transaction transaction) {
        transactions.putIfAbsent(transaction.getTimestamp(), transaction.getAmount());
        transactions.computeIfPresent(transaction.getTimestamp(), (k, v) -> v + transaction.getAmount());

        return transactions;
    }

    public List<Double> getEarlierThanSixtySeconds() {
        return transactions.entrySet().stream()
                .filter(k -> isOlderThanSixtySeconds(k.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private boolean isOlderThanSixtySeconds(Timestamp timestamp) {
        Instant now = Instant.now();
        Duration duration = Duration.between(timestamp.toInstant(), now);

        Duration limit = Duration.ofSeconds(60);

        return duration.compareTo(limit) < 0;
    }
}
