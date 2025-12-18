package com.rob.health_tracker;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDate;


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
        return dailyMetricRepository.findTopByOrderByDateDesc();
    }

    public List<DailyMetric> getBetween(LocalDate from, LocalDate to) {
        return dailyMetricRepository.findByDateBetweenOrderByDateAsc(from, to);
    }


    public void deleteAll() {
        dailyMetricRepository.deleteAll();
}
}
