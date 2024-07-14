package com.mouadhkh.simulator.model;

import com.mouadhkh.simulator.util.Constants;

import static com.mouadhkh.simulator.util.Constants.DEFAULT_MAX_POWER;

public class ChargePoint {

    private ElectricVehicle currentEV;
    private double currentPower;
    private final double maxPower;

    public ChargePoint() {
        this(DEFAULT_MAX_POWER);
    }

    public ChargePoint(int maxPower) {
        this.maxPower = maxPower;
        this.currentEV = null;
        this.currentPower = 0;
    }

    public boolean isAvailable() {
        return currentEV == null;
    }

    public void startCharging(ElectricVehicle ev) {
        this.currentEV = ev;
        this.currentPower = maxPower;
    }

    public double getCurrentPower() {
        return currentPower;
    }


    public void update() {
        if (currentEV != null) {
            double maxEnergyInAnInterval = currentPower * Constants.INTERVAL_DURATION_IN_HOURS;
            currentEV.charge(maxEnergyInAnInterval);
            if (currentEV.isFullyCharged()) {
                stopCharging();
            }
        }
    }

    private void stopCharging() {
        currentEV = null;
        currentPower = 0;
    }
}