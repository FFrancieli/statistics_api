package com.statistics.repository;

import com.statistics.models.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;

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
}
