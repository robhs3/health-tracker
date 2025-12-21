package com.rob.health_tracker;

public class TrendSummaryDto {
    private final Double start;
    private final Double end;
    private final Double change;

    public TrendSummaryDto(Double start, Double end, Double change) {
        this.start = start;
        this.end = end;
        this.change = change;
    }

    public Double getStart() {
        return start;
    }

    public Double getEnd() {
        return end;
    }

    public Double getChange() {
        return change;
    }
}
