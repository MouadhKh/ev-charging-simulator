package com.mouadhkh.simulator.simulation;

import com.mouadhkh.simulator.model.ChargePointMetric;

import java.util.ArrayList;
import java.util.List;

public class SimulationResult {
    private final double totalEnergyConsumed;
    private final double actualMaxPowerDemand;
    private final double theoreticalMaxPowerDemand;
    private final double concurrencyFactor;
    private final List<Integer> dailyChargingEvents;
    private final List<Integer> weeklyChargingEvents;
    private final List<Integer> monthlyChargingEvents;

    private final int yearlyChargingEvents;
    private final List<ChargePointMetric> chargingPointsMetrics;
    private final List<Double> exemplaryDay;


    SimulationResult(SimulationResultBuilder builder) {
        this.totalEnergyConsumed = builder.totalEnergyConsumed;
        this.actualMaxPowerDemand = builder.actualMaxPowerDemand;
        this.theoreticalMaxPowerDemand = builder.theoreticalMaxPowerDemand;
        this.concurrencyFactor = builder.concurrencyFactor;
        this.chargingPointsMetrics = builder.chargingPointsMetrics;
        this.exemplaryDay = builder.exemplaryDay;
        this.dailyChargingEvents = builder.dailyChargingEvents;
        this.weeklyChargingEvents = builder.weeklyChargingEvents;
        this.monthlyChargingEvents = builder.monthlyChargingEvents;
        this.yearlyChargingEvents = builder.yearlyChargingEvents;
    }

    public void print() {
        System.out.printf("Total energy consumed: %.2f kWh%n", totalEnergyConsumed);
        System.out.printf("Theoretical maximum power demand: %.2f kW%n", theoreticalMaxPowerDemand);
        System.out.printf("Actual maximum power demand: %.2f kW%n", actualMaxPowerDemand);
        System.out.printf("Concurrency factor: %.2f%%%n", concurrencyFactor);
    }

    public double getTotalEnergyConsumed() {
        return totalEnergyConsumed;
    }

    public double getActualMaxPowerDemand() {
        return actualMaxPowerDemand;
    }

    public double getTheoreticalMaxPowerDemand() {
        return theoreticalMaxPowerDemand;
    }

    public double getConcurrencyFactor() {
        return concurrencyFactor;
    }

    public List<ChargePointMetric> getChargingPointsMetrics() {
        return chargingPointsMetrics;
    }

    public List<Double> getExemplaryDay() {
        return exemplaryDay;
    }

    public List<Integer> getDailyChargingEvents() {
        return dailyChargingEvents;
    }

    public List<Integer> getWeeklyChargingEvents() {
        return weeklyChargingEvents;
    }

    public List<Integer> getMonthlyChargingEvents() {
        return monthlyChargingEvents;
    }

    public int getYearlyChargingEvents() {
        return yearlyChargingEvents;
    }

    public static SimulationResultBuilder builder() {
        return new SimulationResultBuilder();
    }

    public static class SimulationResultBuilder {
        double totalEnergyConsumed;
        double actualMaxPowerDemand;
        double theoreticalMaxPowerDemand;
        double concurrencyFactor;

        private List<Integer> dailyChargingEvents = new ArrayList<>();
        private List<Integer> weeklyChargingEvents = new ArrayList<>();
        private List<Integer> monthlyChargingEvents = new ArrayList<>();

        private int yearlyChargingEvents;

        private List<ChargePointMetric> chargingPointsMetrics;
        private List<Double> exemplaryDay;

        public SimulationResultBuilder totalEnergyConsumed(double totalEnergyConsumed) {
            this.totalEnergyConsumed = totalEnergyConsumed;
            return this;
        }

        public SimulationResultBuilder actualMaxPowerDemand(double actualMaxPowerDemand) {
            this.actualMaxPowerDemand = actualMaxPowerDemand;
            return this;
        }

        public SimulationResultBuilder theoreticalMaxPowerDemand(double theoreticalMaxPowerDemand) {
            this.theoreticalMaxPowerDemand = theoreticalMaxPowerDemand;
            return this;
        }

        public SimulationResultBuilder concurrencyFactor(double concurrencyFactor) {
            this.concurrencyFactor = concurrencyFactor;
            return this;
        }

        public SimulationResultBuilder chargingPointsMetrics(List<ChargePointMetric> chargingPointsMetrics) {
            this.chargingPointsMetrics = chargingPointsMetrics;
            return this;
        }

        public SimulationResultBuilder exemplaryDay(List<Double> exemplaryDay) {
            this.exemplaryDay = exemplaryDay;
            return this;
        }

        public SimulationResultBuilder chargingEventsBreakdown(
                List<Integer> daily, List<Integer> weekly, List<Integer> monthly, int yearly) {
            this.dailyChargingEvents = daily;
            this.weeklyChargingEvents = weekly;
            this.monthlyChargingEvents = monthly;
            this.yearlyChargingEvents = yearly;
            return this;
        }

        public SimulationResult build() {
            return new SimulationResult(this);
        }
    }
}
