package com.rob.health_tracker;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DailyMetricController {

    private final DailyMetricService dailyMetricService;

    public DailyMetricController(DailyMetricService dailyMetricService) {
        this.dailyMetricService = dailyMetricService;
    }

    // Get all logged daily metrics
    @GetMapping("/api/daily-metrics")
    public List<DailyMetric> getAllMetrics() {
        return dailyMetricService.getAll();
    }

    @GetMapping("/api/daily-metrics/latest")
    public DailyMetric getLatestMetric() {
        return dailyMetricService.getLatest();
    }

    // Add a new daily metric (from JSON)
    @PostMapping("/api/daily-metrics")
    public DailyMetric addMetric(@RequestBody DailyMetric metric) {
        return dailyMetricService.add(metric);
    }

    @GetMapping("/api/health")
    public String health() {
        return "ok";
}

}
