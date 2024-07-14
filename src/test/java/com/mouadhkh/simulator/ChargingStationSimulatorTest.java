package com.mouadhkh.simulator;

import com.mouadhkh.simulator.simulation.SimulationResult;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChargingStationSimulatorTest {


    // Can't deduce anything yet
    @Test
    void testConcurrencyFactorForChargePoints() {
        IntStream.rangeClosed(1, 30).forEach(numChargePoints -> {
            ChargingStationSimulator simulator = new ChargingStationSimulator(numChargePoints);
            SimulationResult result = simulator.run();
            System.out.printf("Charge Points: %d, Concurrency Factor: %.2f%%%n", numChargePoints, result.getConcurrencyFactor());
        });
    }


    @Test
    void testDeterministicResultsWithSeed() {
        Random random1 = new Random(94);
        Random random2 = new Random(94);

        ChargingStationSimulator simulator1 = new ChargingStationSimulator();
        ChargingStationSimulator simulator2 = new ChargingStationSimulator();

        simulator1.getEvManager().setRandom(random1);
        simulator2.getEvManager().setRandom(random2);

        SimulationResult simResult1 = simulator1.run();
        SimulationResult simResult2 = simulator2.run();

        assertEquals(simResult1.getTotalEnergyConsumed(), simResult2.getTotalEnergyConsumed(), "Total energy consumed should be the same for the same seed.");
        assertEquals(simResult1.getActualMaxPowerDemand(), simResult2.getActualMaxPowerDemand(), "Actual max power demand should be the same for the same seed.");
        assertEquals(simResult1.getConcurrencyFactor(), simResult2.getConcurrencyFactor(), "Concurrency factor should be the same for the same seed.");
    }
}
