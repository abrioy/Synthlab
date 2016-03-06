package fr.synthlab.model.module.sequencer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Tests on the sequencer filter
 */
@RunWith(MockitoJUnitRunner.class)
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
        seqFilter = new SEQFilterImpl(stepValues, mockModuleSEQ);
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

    /**
     * test on generate.
     */
    @Test
    public void testGenerate() {
        seqFilter.generate(0,1);

        assertEquals(0, seqFilter.getOutput().getValue(), 0.0000001);
    }

    /**
     * test on generate.
     */
    @Test
    public void testGenerate2() {
        seqFilter.getInput().set(10);
        seqFilter.generate(0,1);

        verify(mockModuleSEQ).updateObs();
        assertEquals(0, seqFilter.getOutput().getValue(), 0.0000001);
    }
}