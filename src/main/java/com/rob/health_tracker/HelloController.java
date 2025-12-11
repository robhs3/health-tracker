package com.rob.health_tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    // In-memory list to store daily metrics
    private final List<DailyMetric> metrics = new ArrayList<>();

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello Rob, your health tracker backend is running!";
    }

    @GetMapping("/api/sample-metric")
    public DailyMetric sampleMetric() {
        return new DailyMetric("2025-12-11", 168.2, 2600, 180);
    }

    // Get all logged daily metrics
    @GetMapping("/api/daily-metrics")
    public List<DailyMetric> getAllMetrics() {
        return metrics;
    }

    @GetMapping("/api/daily-metrics/latest")
    public DailyMetric getLatestMetric() {
        if (metrics.isEmpty()) {
            return null; // no metrics yet
        }
        return metrics.get(metrics.size() - 1);
    }

    // Add a new daily metric (from JSON)
    @PostMapping("/api/daily-metrics")
    public DailyMetric addMetric(@RequestBody DailyMetric metric) {
        metrics.add(metric);
        return metric;
    }
}
