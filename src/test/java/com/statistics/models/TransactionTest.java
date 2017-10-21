package com.statistics.models;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void returnsTrueWhenTimestampIsOlderThan60Seconds() throws Exception {
        Timestamp timestamp = new Timestamp(Instant.now().plusSeconds(90l).toEpochMilli());

        Transaction transaction = new Transaction(12.8, timestamp);

        assertThat(transaction.isOlderThan60Seconds(), is(true));
    }

    @Test
    public void returnsFalseWhenTimestampIsNotOlderThan60Seconds() throws Exception {
        Timestamp timestamp = new Timestamp(Instant.now().toEpochMilli());

        Transaction transaction = new Transaction(12.8, timestamp);

        assertThat(transaction.isOlderThan60Seconds(), is(false));
    }
}