package com.rob.health_tracker;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface DailyMetricRepository extends JpaRepository<DailyMetric, Long> {
    DailyMetric findTopByOrderByDateDesc();
    List<DailyMetric> findByDateBetweenOrderByDateAsc(LocalDate from, LocalDate to);

}
