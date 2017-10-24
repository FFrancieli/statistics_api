package com.statistics.service;

import com.statistics.models.Transaction;
import com.statistics.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    @Autowired
    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity saveTransaction(Transaction transaction) {
        if (transaction.isOlderThan60Seconds()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        repository.save(transaction);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    public List<Double> retrieveTransactionEarlierThanSixtySeconds() {
        return repository.getEarlierThanSixtySeconds();
    }
}