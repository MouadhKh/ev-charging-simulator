package com.mouadhkh.simulator.simulation;

import java.util.Random;

public interface ChargingDemandStrategy {
    double getChargingDemand(Random randomObj);
}
