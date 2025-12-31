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

import com.rob.health_tracker.dto.DailyMetricRequestDto;
import com.rob.health_tracker.dto.DailyMetricResponseDto;
import com.rob.health_tracker.dto.DailyMetricStats;
import com.rob.health_tracker.dto.TrendResponseDto;
import com.rob.health_tracker.entity.DailyMetric;
import com.rob.health_tracker.service.DailyMetricService;
import com.rob.health_tracker.util.CsvDailyMetricRow;
import com.rob.health_tracker.util.DailyMetricCsvParser;
import com.rob.health_tracker.metric.MetricType;
import com.rob.health_tracker.service.DailyMetricCsvImportService;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDate;
import java.io.IOException;
import java.nio.file.Path;



@RestController
@RequestMapping("/api")
public class DailyMetricController {

    private final DailyMetricService dailyMetricService;
    private final DailyMetricCsvImportService dailyMetricCsvImportService;

    @Value("${app.dev-reset-enabled:false}")
    private boolean devResetEnabled;

    @Value("${app.dev-import-enabled:false}")
    private boolean devImportEnabled;

    public DailyMetricController(
        DailyMetricService dailyMetricService,
        DailyMetricCsvImportService dailyMetricCsvImportService
    ) {
        this.dailyMetricService = dailyMetricService;
        this.dailyMetricCsvImportService = dailyMetricCsvImportService;
    }


    // Get all logged daily metrics
    @GetMapping("/daily-metrics")
    public List<DailyMetricResponseDto> getAllMetrics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        if (from != null && to != null) {
            return dailyMetricService.getBetween(from, to);
        }

        return dailyMetricService.getAll();
    }


    @GetMapping("/daily-metrics/latest")
    public DailyMetricResponseDto getLatestMetric() {
        return dailyMetricService.getLatest();
    }


    // Add a new daily metric (from JSON)
    @PostMapping("/daily-metrics")
    public DailyMetric addMetric(@Valid @RequestBody DailyMetricRequestDto metric) {
        return dailyMetricService.add(metric);
    }


    @PostMapping("/dev/import/daily-metrics")
    public void importDailyMetrics() throws IOException {
        if (!devImportEnabled) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Dev import endpoint is disabled"
            );
        }

        Path path = Path.of("src/main/resources/daily-metrics.csv");
        dailyMetricCsvImportService.importFromCsv(path);
    }


    @GetMapping("/daily-metrics/stats")
    public DailyMetricStats getStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return dailyMetricService.getStatsBetween(from, to);
    }


    @GetMapping("/trends/{metric}")
    public TrendResponseDto getTrend(
            @PathVariable String metric,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        
        MetricType metricType;
        try {
            metricType = MetricType.valueOf(metric.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsupported metric: " + metric
            );
        }

        return dailyMetricService.getTrend(metricType, from, to);
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

        // // TEMPORARY IMPORT LOGIC
        // Path path = Path.of("src/main/resources/daily-metrics.csv");
        // try {
        // dailyMetricCsvImportService.importFromCsv(path);
        // }
        // catch (IOException e) {}
        
        
    }


}
