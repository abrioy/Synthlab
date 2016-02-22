package fr.synthlab.model.module.sequencer;

import junit.framework.TestCase;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class SEQFilterTest extends TestCase {

    private SEQFilter seqFilter;

    @Mock
    private ModuleSEQ mockModuleSEQ;

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

    public void testGetCurrent() throws Exception {
        assertEquals(0, seqFilter.getCurrent());
    }

    public void testReset() throws Exception {
        seqFilter.reset();
        assertEquals(0, seqFilter.getCurrent());
    }
}