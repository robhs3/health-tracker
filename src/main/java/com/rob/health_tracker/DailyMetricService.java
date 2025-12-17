package com.rob.health_tracker;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyMetricService {

    private final List<DailyMetric> metrics = new ArrayList<>();

    public List<DailyMetric> getAll() {
        return metrics;
    }

    public DailyMetric add(DailyMetric metric) {
        metrics.add(metric);
        return metric;
    }

    public DailyMetric getLatest() {
        if (metrics.isEmpty()) {
            return null;
        }
        return metrics.get(metrics.size() - 1);
    }
}
