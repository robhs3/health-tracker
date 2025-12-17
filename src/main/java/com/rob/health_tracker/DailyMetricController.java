package com.rob.health_tracker;

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
import jakarta.validation.Valid;


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
    public List<DailyMetric> getAllMetrics() {
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
