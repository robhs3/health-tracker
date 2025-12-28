package com.rob.health_tracker.dto;

import java.time.LocalDate;

public record DailyMetricResponseDto(
    LocalDate date,
    Double weight,
    Integer calories,
    Integer protein
) {}
