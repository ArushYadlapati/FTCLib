package com.seattlesolvers.solverslib.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class LUTTest {
    @Test
    public void getClosest() {
        LUT<Double, Integer> lut = new LUT<>();
        lut.add(0.0, 0);
        lut.add(1.0, 1);
        lut.add(2.0, 2);

        for (int i = 0; i < 50; i++) {
            double d = Math.random() * 2.5; // generate number in range [0, 2.5)
            assertEquals(Math.round(d), lut.getClosest(d).intValue());
        }
    }

    @Test
    public void empty() {
        assertNull(new LUT<Integer, Integer>().getClosest(0));
    }
}
