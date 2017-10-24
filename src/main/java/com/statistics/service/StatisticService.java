package com.statistics.service;

import com.statistics.models.TransactionStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
    TransactionService transactionService;

    @Autowired
    public StatisticService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public ResponseEntity<TransactionStatistics> calculateStatisticsForTransactionsEarlierThanSixtySeconds() {
        transactionService.retrieveTransactionEarlierThanSixtySeconds();

        return null;
    }
}
