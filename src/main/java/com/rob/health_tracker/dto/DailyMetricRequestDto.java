package com.rob.health_tracker.dto;

import java.time.LocalDate;

import jakarta.validation.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record DailyMetricRequestDto(

    @NotNull
    LocalDate date,

    @Positive
    Double weight,

    @PositiveOrZero
    Integer calories,

    @PositiveOrZero
    Integer protein
) {}
