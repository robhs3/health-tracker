package com.rob.health_tracker.dto;

import java.time.LocalDate;
import java.util.List;

public class TrendResponseDto {
    private final String metric;
    private final LocalDate from;
    private final LocalDate to;
    private final List<TrendPointDto> points;
    private final TrendSummaryDto summary;

    public TrendResponseDto(String metric,
                            LocalDate from,
                            LocalDate to,
                            List<TrendPointDto> points,
                            TrendSummaryDto summary) {
        this.metric = metric;
        this.from = from;
        this.to = to;
        this.points = points;
        this.summary = summary;
    }

    public String getMetric() {
        return metric;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public List<TrendPointDto> getPoints() {
        return points;
    }

    public TrendSummaryDto getSummary() {
        return summary;
    }
}
