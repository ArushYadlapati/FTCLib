package com.seattlesolvers.solverslib.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PIDInputOutputTest {
    private PIDController m_controller;

    @Before
    public void setUp() {
        m_controller = new PIDController(0, 0, 0);
    }

    @Test
    public void proportionalGainOutputTest() {
        m_controller.setP(4);

        m_controller.setSetPoint(1000);
        m_controller.setTolerance(1);
        assertFalse(m_controller.atSetPoint());

        assertEquals(-0.1, m_controller.calculate(0.025, 0), 1e-5);
    }

    @Test
    public void integralGainOutputTest() {
        m_controller.setI(4);
        m_controller.setIntegrationBounds(-180, 180);

        double out = 0;

        for (int i = 0; i < 5; i++) {
            out = m_controller.calculate(0.025, 0);
        }

        assertEquals(-0.5 * m_controller.getPeriod(), out, 1e-5);
    }

    @Test
    public void derivativeGainOutputTest() {
        m_controller.setD(4);

        m_controller.setSetPoint(1000);
        m_controller.setTolerance(Double.MAX_VALUE, 1);
        assertFalse(m_controller.atSetPoint());

        // see https://en.wikipedia.org/wiki/Machine_epsilon
        assertEquals(0, m_controller.calculate(0, 0), 2.22e-16);

        assertEquals(m_controller.calculate(0.025, 0), -0.1 / m_controller.getPeriod(), 0.05);
    }

    @Test
    public void errorTolerancePeriodTest() {
        m_controller.setP(0.5);
        // see https://en.wikipedia.org/wiki/Machine_epsilon
        assertEquals(0, m_controller.getPeriod(), 2.22e-16);
        assertEquals(0, m_controller.getVelocityError(), 2.22e-16);
        assertEquals(Double.POSITIVE_INFINITY, m_controller.getTolerance()[1], 2.22e-16);
        m_controller.setSetPoint(100);
        assertEquals(100, m_controller.getPositionError(), 2.22e-16);
        assertEquals(0, m_controller.getPeriod(), 2.22e-16);
        assertEquals(Double.POSITIVE_INFINITY, m_controller.getVelocityError(), 2.22e-16);
        assertFalse(m_controller.atSetPoint());
        m_controller.setTolerance(Double.POSITIVE_INFINITY);
        assertFalse(m_controller.atSetPoint());
        m_controller.setTolerance(5);
        double value = 0;
        do {
            value = value + m_controller.calculate(value);
        } while (!m_controller.atSetPoint());
        assertTrue(m_controller.atSetPoint());
        m_controller.setSetPoint(0);
        assertFalse(m_controller.atSetPoint());
    }
}