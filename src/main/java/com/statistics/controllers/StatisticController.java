package com.statistics.controllers;

import com.statistics.models.TransactionStatistics;
import com.statistics.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statistics")
public class StatisticController {

    private final StatisticService service;

    public StatisticController(StatisticService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<TransactionStatistics> calculateStatistic() {
        return service.calculateStatisticsForTransactionsEarlierThanSixtySeconds();
    }
}
