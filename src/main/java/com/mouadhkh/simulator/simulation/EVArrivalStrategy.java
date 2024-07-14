package com.mouadhkh.simulator.simulation;

import java.util.Random;

public interface EVArrivalStrategy {
    boolean hasArrived(int interval, Random randomObj, double arrivalProbabilityMultiplier);
}
