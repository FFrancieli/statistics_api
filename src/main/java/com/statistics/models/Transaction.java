package com.statistics.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.time.Instant;

public class Transaction {
    private final double amount;
    private final Timestamp timestamp;

    public Transaction(@JsonProperty("amount") double amount, @JsonProperty("timestamp") Timestamp timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public boolean isOlderThan60Seconds() {
        Timestamp nowMinus60Seconds = new Timestamp(Instant.now().minusSeconds(60).toEpochMilli());

        return (timestamp.before(nowMinus60Seconds));
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
