package com.statistics.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionStatistics {
    private double sum;
    private double average;
    private double maximum;
    private double minimum;
    private double count;

    public TransactionStatistics(@JsonProperty("sum") double sum, @JsonProperty("avg") double average,
                                 @JsonProperty("max") double maximum, @JsonProperty("min") double minimum,
                                 @JsonProperty("count") double count) {
        this.sum = sum;
        this.average = average;
        this.maximum = maximum;
        this.minimum = minimum;
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public double getAverage() {
        return average;
    }

    public double getMaximum() {
        return maximum;
    }
}
