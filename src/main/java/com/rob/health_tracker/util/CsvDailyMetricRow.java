package com.rob.health_tracker.util;

public record CsvDailyMetricRow(
            int rowNumber,
            String date,
            String weight,
            String calories,
            String protein
    ) {}
