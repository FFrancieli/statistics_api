package com.statistics.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class StatisticServiceTest {
    @Mock
    TransactionService transactionService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void getsStatisticsEarlierThanSixtySeconds() throws Exception {
        StatisticService service = new StatisticService(transactionService);

        service.calculateStatisticsForTransactionsEarlierThanSixtySeconds();

        verify(transactionService, times(1)).retrieveTransactionEarlierThanSixtySeconds();
    }
}