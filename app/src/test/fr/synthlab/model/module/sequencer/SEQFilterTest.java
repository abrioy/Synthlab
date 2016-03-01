package fr.synthlab.model.module.sequencer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests on the sequencer filter
 */
public class SEQFilterTest {

    /**
     * Filter tested.
     */
    private SEQFilter seqFilter;

    /**
     * Mock
     */
    @Mock
    private ModuleSEQ mockModuleSEQ;

    /**
     * Initialize.
     */
    @Before
    public void setUp()
    {
        List<Double> stepValues = new ArrayList<>();
        stepValues.add(0.0);
        stepValues.add(0.0);
        stepValues.add(0.0);
        stepValues.add(0.0);

        stepValues.add(0.0);
        stepValues.add(0.0);
        stepValues.add(0.0);
        stepValues.add(0.0);
        seqFilter = new SEQFilter(stepValues, mockModuleSEQ);
    }

    /**
     * test on get current
     */
    @Test
    public void testGetCurrent()
    {
        assertEquals(0, seqFilter.getCurrent());
    }

    /**
     * Test on reset
     */
    @Test
    public void testReset()
    {
        seqFilter.reset();
        assertEquals(0, seqFilter.getCurrent());
    }
}