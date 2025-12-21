package com.rob.health_tracker;

import java.time.LocalDate;

public class TrendPointDto {
    private final LocalDate date;
    private final double value;

    public TrendPointDto(LocalDate date, double value) {
        this.date = date;
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }
}
