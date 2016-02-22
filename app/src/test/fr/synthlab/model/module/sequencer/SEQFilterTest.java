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
     * @throws Exception
     */
    public void setUp() throws Exception {
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
     * @throws Exception
     */
    public void testGetCurrent() throws Exception {
        assertEquals(0, seqFilter.getCurrent());
    }

    /**
     * Test on reset
     * @throws Exception
     */
    public void testReset() throws Exception {
        seqFilter.reset();
        assertEquals(0, seqFilter.getCurrent());
    }
}