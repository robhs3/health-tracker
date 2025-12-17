package com.rob.health_tracker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DailyMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private double weight;
    private int calories;
    private int protein;

    // Required no-arg constructor for JSON deserialization
    public DailyMetric() {
    }

    // Convenience constructor
    public DailyMetric(String date, double weight, int calories, int protein) {
        this.date = date;
        this.weight = weight;
        this.calories = calories;
        this.protein = protein;
    }

    // Getters
    public String getDate() {
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
    public void setDate(String date) {
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
