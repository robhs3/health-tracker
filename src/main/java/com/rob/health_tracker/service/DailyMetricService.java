package com.rob.health_tracker.service;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rob.health_tracker.dto.DailyMetricRequestDto;
import com.rob.health_tracker.dto.DailyMetricResponseDto;
import com.rob.health_tracker.dto.DailyMetricStats;
import com.rob.health_tracker.dto.TrendPointDto;
import com.rob.health_tracker.dto.TrendResponseDto;
import com.rob.health_tracker.dto.TrendSummaryDto;
import com.rob.health_tracker.entity.DailyMetric;
import com.rob.health_tracker.repository.DailyMetricRepository;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


@Service
public class DailyMetricService {

    private final DailyMetricRepository dailyMetricRepository;

    public DailyMetricService(DailyMetricRepository dailyMetricRepository) {
        this.dailyMetricRepository = dailyMetricRepository;
    }

    public List<DailyMetricResponseDto> getAll() {
        // Return all metrics, sorted by date ascending
        List<DailyMetric> metricEntities = dailyMetricRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
        List<DailyMetricResponseDto> metricDtos = new ArrayList<>();

        for (DailyMetric e : metricEntities) {
            metricDtos.add(new DailyMetricResponseDto(
                            e.getDate(), 
                            e.getWeight(), 
                            e.getCalories(), 
                            e.getProtein()
            ));
        }

        return metricDtos;
    }

    public DailyMetric add(DailyMetricRequestDto metricRequestDto) {
        DailyMetric entity = new DailyMetric(
                            metricRequestDto.date(), 
                            metricRequestDto.weight(), 
                            metricRequestDto.calories(), 
                            metricRequestDto.protein()
        );

        // save() inserts a new row when id is null, or updates when id is present
        return dailyMetricRepository.save(entity);
    }

    public DailyMetricResponseDto getLatest() {
        // Fetch the most recent metric by date
        DailyMetric metricEntity = dailyMetricRepository.findTopByOrderByDateDesc();
        
        return new DailyMetricResponseDto(
                    metricEntity.getDate(), 
                    metricEntity.getWeight(), 
                    metricEntity.getCalories(), 
                    metricEntity.getProtein()
        );
    }

    public List<DailyMetricResponseDto> getBetween(LocalDate from, LocalDate to) {
        List<DailyMetric> metricEntities = dailyMetricRepository.findByDateBetweenOrderByDateAsc(from, to);
        List<DailyMetricResponseDto> metricDtos = new ArrayList<>();

        for (DailyMetric e : metricEntities) {
            metricDtos.add(new DailyMetricResponseDto(
                            e.getDate(), 
                            e.getWeight(), 
                            e.getCalories(), 
                            e.getProtein()
            ));
        }

        return metricDtos;
    }

    public DailyMetricStats getStatsBetween(LocalDate from, LocalDate to) {
        List<DailyMetricResponseDto> metricDtos = getBetween(from, to);

        int count = metricDtos.size();
        if (count == 0) {
            return new DailyMetricStats(0, 0.0, 0.0, null, null, null);
        }
        
        double totalCalories = 0.0;
        double totalProtein = 0.0;

        for (DailyMetricResponseDto m : metricDtos) {
            totalCalories += m.calories();
            totalProtein += m.protein();
        }

        double avgCalories = totalCalories / count;
        double avgProtein = totalProtein / count;

        Double startWeight = metricDtos.get(0).weight();
        Double endWeight = metricDtos.get(count - 1).weight();
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
