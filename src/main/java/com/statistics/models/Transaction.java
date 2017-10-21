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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public boolean isOlderThan60Seconds() {
        Timestamp nowPlus60Seconds = new Timestamp(Instant.now().plusSeconds(60).toEpochMilli());

        return !(timestamp.before(nowPlus60Seconds));
    }
}
