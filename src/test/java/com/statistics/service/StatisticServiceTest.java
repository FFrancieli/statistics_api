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

    StatisticService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        service = new StatisticService(transactionService);
    }

    @Test
    public void getsStatisticsEarlierThanSixtySeconds() throws Exception {
        service.calculateStatisticsForTransactionsEarlierThanSixtySeconds();

        verify(transactionService, times(1)).retrieveTransactionEarlierThanSixtySeconds();
    }

    @Test
    public void returnsHttpStatusNotFoundIfThereIsNoTransactionEarlierThanSixtySeconds() throws Exception {
        when(transactionService.retrieveTransactionEarlierThanSixtySeconds()).thenReturn(Collections.emptyList());

        ResponseEntity<TransactionStatistics> response = service.calculateStatisticsForTransactionsEarlierThanSixtySeconds();

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void returnsHttpStatusOKIfThereAreTransactionsEarlierThanSixtySeconds() throws Exception {
        when(transactionService.retrieveTransactionEarlierThanSixtySeconds()).thenReturn(Arrays.asList(98.7, 8.8));

        ResponseEntity<TransactionStatistics> response = service.calculateStatisticsForTransactionsEarlierThanSixtySeconds();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void calculatesTheSumOfTransactionsOccurredInThePastSixtySeconds() throws Exception {
        List<Double> transactionData = Arrays.asList(98.1, 95.2, 1.0, 2.0);
        when(transactionService.retrieveTransactionEarlierThanSixtySeconds()).thenReturn(transactionData);

        ResponseEntity<TransactionStatistics> response = service.calculateStatisticsForTransactionsEarlierThanSixtySeconds();

        assertThat(response.getBody().getSum(), is(196.3));
    }

    @Test
    public void calculatesAverageOfTransactionsThatOccuredInThePastSixtySeconds() throws Exception {
        List<Double> transactionData = Arrays.asList(98.1, 95.2, 1.0, 2.0);
        when(transactionService.retrieveTransactionEarlierThanSixtySeconds()).thenReturn(transactionData);

        ResponseEntity<TransactionStatistics> response = service.calculateStatisticsForTransactionsEarlierThanSixtySeconds();

        assertThat(response.getBody().getAverage(), is(49.075));
    }

    @Test
    public void calculatesTheMaximumTransactionThatOccurredInThePastSixtySeconds() throws Exception {
        List<Double> transactionData = Arrays.asList(98.1, 95.2, 1.0, 2.0);
        when(transactionService.retrieveTransactionEarlierThanSixtySeconds()).thenReturn(transactionData);

        ResponseEntity<TransactionStatistics> response = service.calculateStatisticsForTransactionsEarlierThanSixtySeconds();

        assertThat(response.getBody().getMaximum(), is(98.1));
    }

    @Test
    public void calculatesTheMinimumTransactionThatOccurredInThePastSixtySeconds() throws Exception {
        List<Double> transactionData = Arrays.asList(98.1, 95.2, 1.0, 2.0);
        when(transactionService.retrieveTransactionEarlierThanSixtySeconds()).thenReturn(transactionData);

        ResponseEntity<TransactionStatistics> response = service.calculateStatisticsForTransactionsEarlierThanSixtySeconds();

        assertThat(response.getBody().getMinimum(), is(1.0));
    }
}