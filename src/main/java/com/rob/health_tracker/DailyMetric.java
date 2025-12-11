package com.rob.health_tracker;

public class DailyMetric {

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
