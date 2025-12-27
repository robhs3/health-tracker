package com.rob.health_tracker.dto;

public class DailyMetricStats {

    private int countDays;
    private double avgCalories;
    private double avgProtein;

    private Double startWeight;
    private Double endWeight;
    private Double weightChange;

    public DailyMetricStats(int countDays,
                            double avgCalories,
                            double avgProtein,
                            Double startWeight,
                            Double endWeight,
                            Double weightChange) {
        this.countDays = countDays;
        this.avgCalories = avgCalories;
        this.avgProtein = avgProtein;
        this.startWeight = startWeight;
        this.endWeight = endWeight;
        this.weightChange = weightChange;
    }

    public int getCountDays() {
        return countDays;
    }

    public double getAvgCalories() {
        return avgCalories;
    }

    public double getAvgProtein() {
        return avgProtein;
    }

    public Double getStartWeight() {
        return startWeight;
    }

    public Double getEndWeight() {
        return endWeight;
    }

    public Double getWeightChange() {
        return weightChange;
    }
}
