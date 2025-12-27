package com.rob.health_tracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.server.ResponseStatusException;

import com.rob.health_tracker.dto.DailyMetricStats;
import com.rob.health_tracker.dto.TrendResponseDto;
import com.rob.health_tracker.entity.DailyMetric;
import com.rob.health_tracker.service.DailyMetricService;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;



@RestController
@RequestMapping("/api")
public class DailyMetricController {

    private final DailyMetricService dailyMetricService;

    @Value("${app.dev-reset-enabled:false}")
    private boolean devResetEnabled;


    public DailyMetricController(DailyMetricService dailyMetricService) {
        this.dailyMetricService = dailyMetricService;
    }

    // Get all logged daily metrics
    @GetMapping("/daily-metrics")
    public List<DailyMetric> getAllMetrics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        if (from != null && to != null) {
            return dailyMetricService.getBetween(from, to);
        }

        return dailyMetricService.getAll();
    }

    @GetMapping("/daily-metrics/latest")
    public DailyMetric getLatestMetric() {
        return dailyMetricService.getLatest();
    }

    // Add a new daily metric (from JSON)
    @PostMapping("/daily-metrics")
    public DailyMetric addMetric(@Valid @RequestBody DailyMetric metric) {
        return dailyMetricService.add(metric);
    }

    @GetMapping("/daily-metrics/stats")
    public DailyMetricStats getStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return dailyMetricService.getStatsBetween(from, to);
    }

    @GetMapping("/trends/weight")
    public TrendResponseDto getWeightTrend(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return dailyMetricService.getWeightTrend(from, to);
    }

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @DeleteMapping("/daily-metrics")
    public void deleteAllMetrics() {
        if (!devResetEnabled) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Dev reset endpoint is disabled");
        }
        dailyMetricService.deleteAll();
    }


}
