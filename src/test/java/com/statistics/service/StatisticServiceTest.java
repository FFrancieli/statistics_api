package com.statistics.service;

import com.statistics.models.TransactionStatistics;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

    @Test
    public void returnsHttpStatusNotFoundIfThereIsNoTransactionEarlierThanSixtySeconds() throws Exception {
        when(transactionService.retrieveTransactionEarlierThanSixtySeconds()).thenReturn(Collections.emptyList());

        StatisticService service = new StatisticService(transactionService);

        ResponseEntity<TransactionStatistics> response = service.calculateStatisticsForTransactionsEarlierThanSixtySeconds();

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void returnsHttpStatusOKIfThereAreTransactionsEarlierThanSixtySeconds() throws Exception {
        when(transactionService.retrieveTransactionEarlierThanSixtySeconds()).thenReturn(Arrays.asList(98.7, 8.8));

        StatisticService service = new StatisticService(transactionService);

        ResponseEntity<TransactionStatistics> response = service.calculateStatisticsForTransactionsEarlierThanSixtySeconds();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}