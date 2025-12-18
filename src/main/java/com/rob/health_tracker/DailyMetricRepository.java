package com.rob.health_tracker;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyMetricRepository extends JpaRepository<DailyMetric, Long> {
    DailyMetric findTopByOrderByDateDesc();

}
