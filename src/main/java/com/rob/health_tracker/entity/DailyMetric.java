package com.rob.health_tracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
    name = "daily_metric",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_daily_metric_date", columnNames = {"date"})
    }
)
public class DailyMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private LocalDate date;

    @Min(0)
    private double weight;

    @Min(0)
    private int calories;

    @Min(0)
    private int protein;

    // Required no-arg constructor for JSON deserialization
    public DailyMetric() {
    }

    // Convenience constructor
    public DailyMetric(LocalDate date, double weight, int calories, int protein) {
        this.date = date;
        this.weight = weight;
        this.calories = calories;
        this.protein = protein;
    }

    // Getters
    public LocalDate getDate() {
    return date;
    }

    public double getWeight() {
        return weight;
    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public Long getId() {
    return id;
    }

    // Setters
    public void setDate(LocalDate date) {
    this.date = date;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }
}
