package fr.synthlab.model.module.vca;

import com.jsyn.ports.UnitOutputPort;
import com.softsynth.math.AudioMath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * test on FilterVCAam.
 */
@RunWith(MockitoJUnitRunner.class)
public class FilterVCAamTest {

    /**
     * mock unit output.
     */
    @Mock
    private UnitOutputPort mockOutputPort;

    /**
     * filter tested
     */
    private FilterVCAam filter;

    /**
     * initialize.
     */
    @Before
    public void setUp() {
        filter = new FilterVCAam(mockOutputPort);
    }

    /**
     * test on generate.
     */
    @Test
    public void testGenerate() {
        when(mockOutputPort.getValues()).thenReturn(new double[]{10.0});
        filter.generate(0,1);

        assertEquals(0.0, filter.getOutput().get(), 0.0001);
    }

    /**
     * test on generate.
     */
    @Test
    public void testGenerate2() {
        when(mockOutputPort.getValues()).thenReturn(new double[]{10.0});
        filter.getInput().set(5.0);
        filter.generate(0,1);

        assertEquals(10 * AudioMath.decibelsToAmplitude(5 * 12), filter.getOutput().get(), 0.0001);
    }

    /**
     * test on generate.
     */
    @Test
    public void testGenerate3() {
        when(mockOutputPort.getValues()).thenReturn(new double[]{10.0});
        filter.getInput().set(-5.0);
        filter.generate(0,1);

        assertEquals(10 * AudioMath.decibelsToAmplitude(-5 * 12), filter.getOutput().get(), 0.0001);
    }
}