package com.mouadhkh.simulator.simulation;

import com.mouadhkh.simulator.model.ChargingDemand;

import java.util.Random;

public class FixedChargingDemandStrategy implements ChargingDemandStrategy {

    @Override
    public double getChargingDemand(Random randomObj) {
        double cumulativeProbability = 0;
        double random = randomObj.nextDouble();
        for (ChargingDemand demand : ProbabilityDistributions.FIXED_CHARGING_DEMANDS_DISTRIBUTION) {
            cumulativeProbability += demand.getProbability();
            if (random < cumulativeProbability) {
                return demand.getDistance();
            }
        }
        return 0;
    }
}
