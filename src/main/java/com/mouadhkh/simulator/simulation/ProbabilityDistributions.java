package com.mouadhkh.simulator.simulation;

import com.mouadhkh.simulator.model.ChargingDemand;
import com.mouadhkh.simulator.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mouadhkh.simulator.util.Constants.HOURS_IN_A_DAY;
import static com.mouadhkh.simulator.util.Constants.INTERVALS_PER_HOUR;

public class ProbabilityDistributions {


    public static final Map<Integer, Double> TIME_INTERVAL_BASED_ARRIVAL_DISTRIBUTION = getTimeIntervalBasedArrivalDistribution();
    public static final List<ChargingDemand> FIXED_CHARGING_DEMANDS_DISTRIBUTION = List.of(
            new ChargingDemand(0, 0.3431),
            new ChargingDemand(5, 0.0490),
            new ChargingDemand(10, 0.0980),
            new ChargingDemand(20, 0.1176),
            new ChargingDemand(30, 0.0882),
            new ChargingDemand(50, 0.1176),
            new ChargingDemand(100, 0.1078),
            new ChargingDemand(200, 0.0490),
            new ChargingDemand(300, 0.0294)
    );

    private static Map<Integer, Double> getTimeIntervalBasedArrivalDistribution() {
        HashMap<Integer, Double> probabilityDistribution = new HashMap<>();
        double[] hourlyProbabilities = {
                0.0094, 0.0094, 0.0094, 0.0094, 0.0094, 0.0094, 0.0094, 0.0094,
                0.0283, 0.0283, 0.0566, 0.0566, 0.0566, 0.0755, 0.0755, 0.0755,
                0.1038, 0.1038, 0.1038, 0.0472, 0.0472, 0.0472, 0.0094, 0.0094
        };
        for (int hour = 0; hour < HOURS_IN_A_DAY; hour++) {
            double intervalProbability = hourlyProbabilities[hour] / INTERVALS_PER_HOUR;
            for (int quarter = 0; quarter < INTERVALS_PER_HOUR; quarter++) {
                probabilityDistribution.put(hour * INTERVALS_PER_HOUR + quarter, intervalProbability);
            }
        }
        return probabilityDistribution;
    }
}
