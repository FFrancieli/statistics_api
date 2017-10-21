package com.statistics.service;

import com.statistics.models.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    public ResponseEntity cacheTransaction(Transaction transaction) {
        if (transaction.isOlderThan60Seconds()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return null;
    }
}