package com.rob.health_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rob.health_tracker.entity.DailyMetric;

import java.time.LocalDate;
import java.util.List;

public interface DailyMetricRepository extends JpaRepository<DailyMetric, Long> {
    DailyMetric findTopByOrderByDateDesc();
    List<DailyMetric> findByDateBetweenOrderByDateAsc(LocalDate from, LocalDate to);

}
