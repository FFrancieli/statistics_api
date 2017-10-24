package com.statistics.repository;

import com.statistics.models.Transaction;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TransactionRepositoryTest {

    @Test
    public void savesTransaction() throws Exception {
        ConcurrentHashMap concurrentHashMap = mock(ConcurrentHashMap.class);
        TransactionRepository repository = new TransactionRepository(concurrentHashMap);

        Transaction transaction = new Transaction(92.7, new Timestamp(1478192204000l));

        repository.save(transaction);

        verify(concurrentHashMap).putIfAbsent(any(Timestamp.class), anyDouble());
    }

    @Test
    public void sumsAmountToExistentOnItTransactionTimestampAlreadyExists() throws Exception {
        Timestamp existentTimestamp = new Timestamp(1478192204000l);

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put(existentTimestamp, 10.0);

        Transaction transaction = new Transaction(12.0, existentTimestamp);
        TransactionRepository repository = new TransactionRepository(concurrentHashMap);
        ConcurrentHashMap savedTransactions = repository.save(transaction);

        assertThat(savedTransactions.get(existentTimestamp), is(22.0));
    }
}