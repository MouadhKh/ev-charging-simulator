package com.mouadhkh.simulator.util;

import com.mouadhkh.simulator.simulation.ChargingDemandStrategy;
import com.mouadhkh.simulator.simulation.EVArrivalStrategy;

import java.util.Random;

public class EVManager {
    private EVArrivalStrategy arrivalStrategy;
    private ChargingDemandStrategy chargingDemandStrategy;
    private Random random;
    private final double arrivalProbabilityMultiplier;


    public void setRandom(Random random) {
        this.random = random;
    }

    public EVManager(EVArrivalStrategy arrivalStrategy, ChargingDemandStrategy chargingDemandStrategy, Random random, double arrivalProbabilityMultiplier) {
        this.arrivalStrategy = arrivalStrategy;
        this.chargingDemandStrategy = chargingDemandStrategy;
        this.random = random;
        this.arrivalProbabilityMultiplier = arrivalProbabilityMultiplier;
    }

    public boolean hasArrived(int interval) {
        return arrivalStrategy.hasArrived(interval, random, arrivalProbabilityMultiplier);
    }

    public double getChargingDemand() {
        return chargingDemandStrategy.getChargingDemand(random);
    }

    public Random getRandom() {
        return random;
    }

    public EVArrivalStrategy getArrivalStrategy() {
        return arrivalStrategy;
    }

    public void setArrivalStrategy(EVArrivalStrategy arrivalStrategy) {
        this.arrivalStrategy = arrivalStrategy;
    }

    public ChargingDemandStrategy getChargingDemandStrategy() {
        return chargingDemandStrategy;
    }

    public void setChargingDemandStrategy(ChargingDemandStrategy chargingDemandStrategy) {
        this.chargingDemandStrategy = chargingDemandStrategy;
    }

}