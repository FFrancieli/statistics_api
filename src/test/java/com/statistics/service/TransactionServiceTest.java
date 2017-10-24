package com.statistics.service;

import com.statistics.models.Transaction;
import com.statistics.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.Instant;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class TransactionServiceTest {

    @Mock
    TransactionRepository repository;

    private TransactionService service;
    public static final Timestamp TIMESTAMP_OLDER_THAN_SIXTY_SECONDS = new Timestamp(Instant.now().minusSeconds(90l).toEpochMilli());
    public static final Timestamp TIMESTAMP_EARLIER_THAN_SIXTY_SECONDS = new Timestamp(Instant.now().minusSeconds(10l).toEpochMilli());

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new TransactionService(repository);
    }

    @Test
    public void returnsHttpStatus204IfTransactionIsOlderThan60Seconds() throws Exception {
        Transaction transaction = new Transaction(10.0, TIMESTAMP_OLDER_THAN_SIXTY_SECONDS);

        ResponseEntity response = service.saveTransaction(transaction);

        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    public void returnsHttpStatus201IfTransactionIsNotOlderThan60Seconds() throws Exception {
        Timestamp timestamp = new Timestamp(Instant.now().minusSeconds(10l).toEpochMilli());
        Transaction transaction = new Transaction(10.0, timestamp);

        ResponseEntity response = service.saveTransaction(transaction);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    public void shouldSaveTransactionWhenItIsEarlierThanSixtySeconds() throws Exception {
        Transaction transaction = new Transaction(10.0, TIMESTAMP_EARLIER_THAN_SIXTY_SECONDS);

        service.saveTransaction(transaction);

        verify(repository, times(1)).save(transaction);
    }
}