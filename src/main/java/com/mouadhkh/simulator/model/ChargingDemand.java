package com.mouadhkh.simulator.model;

public class ChargingDemand {
    private double kilometers;
    private double probability;

    public ChargingDemand(double kilometers, double probability) {
        this.kilometers = kilometers;
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }

    public double getDistance() {
        return kilometers; // Convert kilometers to kWh
    }

    public void setKilometers(double kilometers) {
        this.kilometers = kilometers;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
