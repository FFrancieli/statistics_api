package com.statistics.service;

import com.statistics.models.TransactionStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {
    TransactionService transactionService;

    @Autowired
    public StatisticService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public ResponseEntity<TransactionStatistics> calculateStatisticsForTransactionsEarlierThanSixtySeconds() {
        List<Double> transactionData = transactionService.retrieveTransactionEarlierThanSixtySeconds();
        if (transactionData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        double sum = sumTransactionAmount(transactionData);

        TransactionStatistics statistics = new TransactionStatistics(sum, 0, 0, 0, 0);

        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    private double sumTransactionAmount(List<Double> transactionData) {
        return transactionData.parallelStream().reduce(0.0, Double::sum);
    }
}
