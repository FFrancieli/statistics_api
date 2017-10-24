package com.statistics.service;

import com.statistics.models.TransactionStatistics;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
    public ResponseEntity<TransactionStatistics> calculateStatisticsForTransactionsEarlierThanSixtySeconds() {
        return null;
    }
}
