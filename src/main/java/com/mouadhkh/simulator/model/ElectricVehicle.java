package com.mouadhkh.simulator.model;

public class ElectricVehicle {
    private double chargingNeedsInKWh;

    public ElectricVehicle(double chargingNeedsInKm) {
        this.chargingNeedsInKWh = chargingNeedsInKm * 0.18;
    }

    public double getChargingNeedsInKWh() {
        return chargingNeedsInKWh;
    }

    public void charge(double energy) {
        chargingNeedsInKWh -= energy;
        if (chargingNeedsInKWh < 0) {
            chargingNeedsInKWh = 0;
        }
    }

    public boolean isFullyCharged() {
        return chargingNeedsInKWh == 0;
    }
}