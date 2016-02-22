package fr.synthlab.model.module.sequencer;

import junit.framework.TestCase;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests on the sequencer filter
 */
public class SEQFilterTest extends TestCase {

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
    public void testGetCurrent()
    {
        assertEquals(0, seqFilter.getCurrent());
    }

    /**
     * Test on reset
     */
    public void testReset()
    {
        seqFilter.reset();
        assertEquals(0, seqFilter.getCurrent());
    }
}