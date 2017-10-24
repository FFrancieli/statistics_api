package com.statistics.controllers;

import com.statistics.models.TransactionStatistics;
import com.statistics.service.StatisticService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class StatisticControllerTest {

    @Mock
    private StatisticService service;

    private StatisticController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        controller = new StatisticController(service);
    }

    @Test
    public void calculatesTransactionStatistics() throws Exception {
        controller.calculateStatistic();

        verify(service, times(1)).calculateStatisticsForTransactionsEarlierThanSixtySeconds();
    }

    @Test
    public void returnsHttpStatusNotFoundWhenThereIsNoTransactionEarlierThan60Seconds() throws Exception {
        when(service.calculateStatisticsForTransactionsEarlierThanSixtySeconds())
                .thenReturn(new ResponseEntity(HttpStatus.NOT_FOUND));

        ResponseEntity  response = controller.calculateStatistic();

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void returnsHttpStatusOkWhenThereAreTransactionsEarlierThan60Seconds() throws Exception {
        when(service.calculateStatisticsForTransactionsEarlierThanSixtySeconds()).thenReturn(new ResponseEntity(HttpStatus.OK));

        ResponseEntity  response = controller.calculateStatistic();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void returnsStatisticsCalculationAsResponseBody() throws Exception {
        TransactionStatistics statistics = new TransactionStatistics(1000.0, 56.9, 89.0, 1.0, 8.0);

        when(service.calculateStatisticsForTransactionsEarlierThanSixtySeconds())
                .thenReturn(new ResponseEntity(statistics, HttpStatus.OK));

        ResponseEntity  response = controller.calculateStatistic();

        assertThat(response.getBody(), samePropertyValuesAs(statistics));
    }
}