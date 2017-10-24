package com.statistics.repository;

import com.statistics.models.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TransactionRepositoryTest {

    @Test
    public void savesTransaction() throws Exception {
        ConcurrentMap transactions = mock(ConcurrentHashMap.class);
        TransactionRepository repository = new TransactionRepository(transactions);

        Transaction transaction = new Transaction(92.7, new Timestamp(1478192204000l));

        repository.save(transaction);

        verify(transactions).putIfAbsent(any(Timestamp.class), anyDouble());
    }

    @Test
    public void sumsAmountToExistentOnItTransactionTimestampAlreadyExists() throws Exception {
        Timestamp existentTimestamp = new Timestamp(1478192204000l);

        ConcurrentMap transactions = new ConcurrentHashMap();
        transactions.put(existentTimestamp, 10.0);

        Transaction transaction = new Transaction(12.0, existentTimestamp);
        TransactionRepository repository = new TransactionRepository(transactions);
        ConcurrentMap savedTransactions = repository.save(transaction);

        assertThat(savedTransactions.get(existentTimestamp), is(22.0));
    }

    @Test
    public void name() throws Exception {
        Timestamp oldTimestamp = new Timestamp(1478192204000l);
        Timestamp recentTimestamp =  new Timestamp(Instant.now().minusSeconds(10l).toEpochMilli());

        ConcurrentMap<Timestamp, Double> transactions = new ConcurrentHashMap<>();
        transactions.put(oldTimestamp, 89.0);
        transactions.put(recentTimestamp, 98.0);

        TransactionRepository repository = new TransactionRepository(transactions);
        List<Double> transactionAmount = repository.getEarlierThanSixtySeconds();

        assertThat(transactionAmount, hasItem(98.0));
        assertThat(transactionAmount.size(), is(1));
    }

    @Test
    public void returnEmptyListWhenThereIsNotTransctionEarlierThanSixtySeconds() throws Exception {
        Timestamp oldTimestamp = new Timestamp(1478192204000l);

        ConcurrentMap<Timestamp, Double> transactions = new ConcurrentHashMap<>();
        transactions.put(oldTimestamp, 89.0);

        TransactionRepository repository = new TransactionRepository(transactions);
        List<Double> transactionAmount = repository.getEarlierThanSixtySeconds();

        assertThat(transactionAmount.size(), is(0));
    }
}