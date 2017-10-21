package com.statistics.service;

import com.statistics.models.Transaction;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.Instant;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TransactionServiceTest {

    @Test
    public void returnsHttpStatus204IfTransactionIsOlderThan60Seconds() throws Exception {
        Timestamp timestamp = new Timestamp(Instant.now().plusSeconds(90l).toEpochMilli());
        Transaction transaction = new Transaction(10.0, timestamp);

        TransactionService service = new TransactionService();

        ResponseEntity response = service.cacheTransaction(transaction);

        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }
}