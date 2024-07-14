package com.mouadhkh.simulator.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TimeIntervalBasedArrivalStrategyTest {

    private TimeIntervalBasedArrivalStrategy timeIntervalBasedArrivalStrategy;
    private Random mockRandom;

    @BeforeEach
    public void setUp() {
        timeIntervalBasedArrivalStrategy = new TimeIntervalBasedArrivalStrategy();
        mockRandom = Mockito.mock(Random.class);
    }

    @Test
    public void testHasArrivedShouldReturnTrue() {
        // 0.002 < 0.0094/4(arrival probability for first interval)
        when(mockRandom.nextDouble()).thenReturn(0.002);
        boolean hasArrived = timeIntervalBasedArrivalStrategy.hasArrived(0, mockRandom, 1.0);
        assertTrue(hasArrived);
    }

    @Test
    public void testHasArrivedShouldReturnFalse() {
        // 0.05 > 0.0094/4(arrival probability for first interval)
        when(mockRandom.nextDouble()).thenReturn(0.05);
        boolean hasArrived = timeIntervalBasedArrivalStrategy.hasArrived(0, mockRandom, 1.0);
        assertFalse(hasArrived);
    }

}
