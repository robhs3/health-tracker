package com.rob.health_tracker;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    public DailyMetricStats getStatsBetween(LocalDate from, LocalDate to) {
        List<DailyMetric> metrics = getBetween(from, to);

        int count = metrics.size();
        if (count == 0) {
            return new DailyMetricStats(0, 0.0, 0.0, null, null, null);
        }

        double avgCalories = metrics.stream()
                .mapToInt(DailyMetric::getCalories)
                .average()
                .orElse(0.0);

        double avgProtein = metrics.stream()
                .mapToInt(DailyMetric::getProtein)
                .average()
                .orElse(0.0);

        Double startWeight = metrics.get(0).getWeight();
        Double endWeight = metrics.get(count - 1).getWeight();
        Double weightChange = endWeight - startWeight;

        return new DailyMetricStats(count, avgCalories, avgProtein, startWeight, endWeight, weightChange);
    }

    public TrendResponseDto getWeightTrend(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid date range"
            );
        }

        List<DailyMetric> metrics = dailyMetricRepository.findByDateBetweenOrderByDateAsc(from, to);

        if (metrics.size() == 0) {
            return new TrendResponseDto("weight", from, to, null, null);
        }

        List<TrendPointDto> points = new ArrayList<>();

        for (DailyMetric m : metrics) {
            TrendPointDto point = new TrendPointDto(m.getDate(), m.getWeight());
            points.add(point);
        }
        
        double start = points.get(0).getValue();
        double end = points.get((points.size() - 1)).getValue();

        TrendSummaryDto summary = new TrendSummaryDto(start, end, end - start);
        return new TrendResponseDto("weight", from, to, points, summary);
    }
    
    public void deleteAll() {
        dailyMetricRepository.deleteAll();
}
}
