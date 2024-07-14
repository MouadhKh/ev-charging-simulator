package com.mouadhkh.simulator;

import com.mouadhkh.simulator.model.ChargePoint;
import com.mouadhkh.simulator.model.ChargePointMetric;
import com.mouadhkh.simulator.model.ElectricVehicle;
import com.mouadhkh.simulator.simulation.FixedChargingDemandStrategy;
import com.mouadhkh.simulator.simulation.SimulationResult;
import com.mouadhkh.simulator.simulation.TimeIntervalBasedArrivalStrategy;
import com.mouadhkh.simulator.util.Constants;
import com.mouadhkh.simulator.util.EVManager;

import java.util.*;
import java.util.stream.IntStream;

import static com.mouadhkh.simulator.util.Constants.*;

public class ChargingStationSimulator {

    private static final int DEFAULT_NUM_CHARGING_STATIONS = 20;
    private static final int INTERVALS_PER_YEAR = 35040;//24*4*365
    private static final int MAX_POWER_PER_CHARGING_STATION = 11;
    private static final double DEFAULT_ARRIVAL_PROBABILITY_MULTIPLIER = 1.0;

    private final List<ChargePoint> chargePoints;
    private final EVManager evManager;

    public ChargingStationSimulator(int chargingStationsNumber, double arrivalProbabilityMultiplier) {
        this.chargePoints = IntStream.range(0, chargingStationsNumber).mapToObj(cp -> new ChargePoint()).toList();
        Random random = new Random();
        this.evManager = new EVManager(new TimeIntervalBasedArrivalStrategy(), new FixedChargingDemandStrategy(), random, arrivalProbabilityMultiplier);
    }

    public ChargingStationSimulator() {
        this(DEFAULT_NUM_CHARGING_STATIONS, DEFAULT_ARRIVAL_PROBABILITY_MULTIPLIER);
    }

    public ChargingStationSimulator(int chargingStationsNumber) {
        this(chargingStationsNumber, DEFAULT_ARRIVAL_PROBABILITY_MULTIPLIER);
    }

    public SimulationResult run() {
        SimulationState state = initializeState();
        for (int interval = 0; interval < INTERVALS_PER_YEAR; interval++) {
            processInterval(state, interval);
        }
        finalizeEvents(state);
        return buildResult(state);
    }

    private SimulationState initializeState() {
        SimulationState state = new SimulationState();
        state.totalEnergyConsumed = 0;
        state.actualMaxPowerDemand = 0;
        state.theoreticalMaxPowerDemand = DEFAULT_NUM_CHARGING_STATIONS * MAX_POWER_PER_CHARGING_STATION;
        state.currentDayEvents = 0;
        state.currentWeekEvents = 0;
        state.currentMonthEvents = 0;
        state.totalChargingEvents = 0;
        state.dailyChargingEvents = new ArrayList<>();
        state.weeklyChargingEvents = new ArrayList<>();
        state.monthlyChargingEvents = new ArrayList<>();
        state.chargingValuesPerChargePoint = new ArrayList<>();
        state.exemplaryDay = new ArrayList<>();
        for (int i = 0; i < chargePoints.size(); i++) {
            state.chargingValuesPerChargePoint.add(i, new ArrayList<>());
        }
        return state;
    }

    private void processInterval(SimulationState state, int interval) {
        double intervalPowerDemand = 0;
        for (int chargePointId = 0; chargePointId < chargePoints.size(); chargePointId++) {
            ChargePoint chargePoint = chargePoints.get(chargePointId);
            if (evManager.hasArrived(interval) && chargePoint.isAvailable()) {
                double chargingDemand = evManager.getChargingDemand();
                if (chargingDemand > 0) {
                    chargePoint.startCharging(new ElectricVehicle(chargingDemand));
                    state.currentDayEvents++;
                    state.currentWeekEvents++;
                    state.currentMonthEvents++;
                    state.totalChargingEvents++;
                }
            }
            intervalPowerDemand += chargePoint.getCurrentPower();
            state.chargingValuesPerChargePoint.get(chargePointId).add(chargePoint.getCurrentPower());
            chargePoint.update();
        }

        updateChargingEvents(state, interval);
        state.totalEnergyConsumed += intervalPowerDemand * Constants.INTERVAL_DURATION_IN_HOURS;
        state.actualMaxPowerDemand = Math.max(state.actualMaxPowerDemand, intervalPowerDemand);

        // we take the first day as exemplary day
        if (interval < INTERVALS_PER_DAY) {
            state.exemplaryDay.add(intervalPowerDemand);
        }
    }

    private void updateChargingEvents(SimulationState state, int interval) {
        // reset current day events on new day
        if ((interval + 1) % INTERVALS_PER_DAY == 0) {
            state.dailyChargingEvents.add(state.currentDayEvents);
            state.currentDayEvents = 0;
        }
        // reset current week events on new week
        if ((interval + 1) % INTERVALS_PER_WEEK == 0) {
            state.weeklyChargingEvents.add(state.currentWeekEvents);
            state.currentWeekEvents = 0;
        }
        // reset current week events on new month
        if ((interval + 1) % (INTERVALS_PER_DAY * 30) == 0) {
            state.monthlyChargingEvents.add(state.currentMonthEvents);
            state.currentMonthEvents = 0;
        }
    }

    private void finalizeEvents(SimulationState state) {
        // add remaining daily charging events to last day
        if (state.currentDayEvents > 0) {
            int lastIndex = state.dailyChargingEvents.size() - 1;
            state.dailyChargingEvents.set(lastIndex, state.dailyChargingEvents.get(lastIndex) + state.currentDayEvents);
        }
        // add remaining weekly charging events to last week
        if (state.currentWeekEvents > 0) {
            int lastIndex = state.weeklyChargingEvents.size() - 1;
            state.weeklyChargingEvents.set(lastIndex, state.weeklyChargingEvents.get(lastIndex) + state.currentWeekEvents);
        }
        // add remaining monthly charging events to last month
        if (state.currentMonthEvents > 0) {
            int lastIndex = state.monthlyChargingEvents.size() - 1;
            state.monthlyChargingEvents.set(lastIndex, state.monthlyChargingEvents.get(lastIndex) + state.currentMonthEvents);
        }
    }

    private SimulationResult buildResult(SimulationState state) {
        double concurrencyFactor = (state.actualMaxPowerDemand / state.theoreticalMaxPowerDemand) * 100;
        List<ChargePointMetric> chargingPointMetrics = ChargePointMetric.createMetricsList(state.chargingValuesPerChargePoint);

        return SimulationResult.builder()
                .totalEnergyConsumed(state.totalEnergyConsumed)
                .theoreticalMaxPowerDemand(state.theoreticalMaxPowerDemand)
                .actualMaxPowerDemand(state.actualMaxPowerDemand)
                .concurrencyFactor(concurrencyFactor)
                .chargingPointsMetrics(chargingPointMetrics)
                .exemplaryDay(state.exemplaryDay)
                .chargingEventsBreakdown(state.dailyChargingEvents, state.weeklyChargingEvents, state.monthlyChargingEvents, state.totalChargingEvents)
                .build();
    }

    public List<ChargePoint> getChargePoints() {
        return chargePoints;
    }

    public EVManager getEvManager() {
        return evManager;
    }

    public static void main(String[] args) {
        ChargingStationSimulator simulator = new ChargingStationSimulator();
        SimulationResult simulationResult = simulator.run();
        simulationResult.print();
    }

    private static class SimulationState {
        double totalEnergyConsumed;
        double actualMaxPowerDemand;
        double theoreticalMaxPowerDemand;
        int currentDayEvents;
        int currentWeekEvents;
        int currentMonthEvents;
        int totalChargingEvents;
        List<Integer> dailyChargingEvents;
        List<Integer> weeklyChargingEvents;
        List<Integer> monthlyChargingEvents;
        List<List<Double>> chargingValuesPerChargePoint;
        List<Double> exemplaryDay;
    }
}