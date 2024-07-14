package com.mouadhkh.simulator.model;


import java.util.List;
import java.util.stream.Collectors;

public class ChargePointMetric {
    double utilizationRate;
    double averagePower;
    //Add other metrics


    public ChargePointMetric(double utilizationRate, double averagePower) {
        this.utilizationRate = utilizationRate;
        this.averagePower = averagePower;
    }

    public double getUtilizationRate() {
        return utilizationRate;
    }

    public void setUtilizationRate(double utilizationRate) {
        this.utilizationRate = utilizationRate;
    }

    public double getAveragePower() {
        return averagePower;
    }

    public void setAveragePower(double averagePower) {
        this.averagePower = averagePower;
    }

    public static List<ChargePointMetric> createMetricsList(List<List<Double>> chargingValues) {
        return chargingValues.stream().map(values -> {
                    long nonZeroCount = values.stream().filter(value -> value > 0).count();
                    double utilizationRate = (double) nonZeroCount / values.size();
                    double averagePower = values.stream()
                            .mapToDouble(Double::doubleValue)
                            .average()
                            .orElse(0.0);
                    return new ChargePointMetric(utilizationRate, averagePower);
                })
                .collect(Collectors.toList());
    }
}