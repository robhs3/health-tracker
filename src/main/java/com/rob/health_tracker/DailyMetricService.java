package com.rob.health_tracker;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyMetricService {

    private final DailyMetricRepository dailyMetricRepository;

    public DailyMetricService(DailyMetricRepository dailyMetricRepository) {
        this.dailyMetricRepository = dailyMetricRepository;
    }

    public List<DailyMetric> getAll() {
        // Return all metrics, sorted by date ascending (lexicographically for now)
        return dailyMetricRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
    }

    public DailyMetric add(DailyMetric metric) {
        // save() inserts a new row when id is null, or updates when id is present
        return dailyMetricRepository.save(metric);
    }

    public DailyMetric getLatest() {
        // Fetch the most recent metric by date (works best once date is a real date type)
        List<DailyMetric> results =
                dailyMetricRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));

        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }
}
