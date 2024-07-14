package com.mouadhkh.simulator.simulation;

import java.util.Random;

import static com.mouadhkh.simulator.simulation.ProbabilityDistributions.TIME_INTERVAL_BASED_ARRIVAL_DISTRIBUTION;
import static com.mouadhkh.simulator.util.Constants.HOURS_IN_A_DAY;
import static com.mouadhkh.simulator.util.Constants.INTERVALS_PER_HOUR;


public class TimeIntervalBasedArrivalStrategy implements EVArrivalStrategy {


    @Override
    public boolean hasArrived(int interval, Random randomObj, double arrivalProbabilityMultiplier) {
        int dayInterval = interval % (HOURS_IN_A_DAY * INTERVALS_PER_HOUR);
        double adjustedProbability = TIME_INTERVAL_BASED_ARRIVAL_DISTRIBUTION
                .get(dayInterval) * arrivalProbabilityMultiplier;
        return randomObj.nextDouble() < adjustedProbability;
    }
}