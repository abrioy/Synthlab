package fr.synthlab.model.filter;

import com.softsynth.math.AudioMath;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * test on FilterFM.
 */
public class FilterFmTest {

    /**
     * filter tested.
     */
    private FilterFm filter;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        filter = new FilterFm(450);
    }

    /**
     * test on generate
     */
    @Test
    public void testGenerate() {
        filter.getInput().set(3.0);
        filter.generate(0,1);

        assertEquals(3600.0, filter.getOutput().getValue(0), 0.000001);
    }

    /**
     * test on generate
     */
    @Test
    public void testGenerate2() {
        filter.setF0(250.0);
        filter.getInput().set(3.0);
        filter.generate(0,1);

        assertEquals(2000.0, filter.getOutput().getValue(0), 0.0000000001);
    }

    /**
     * test on generate
     */
    @Test
    public void testGenerate3() {
        filter.setF0(250.0);
        filter.getInput().set(50.0);
        filter.generate(1,0);

        assertEquals(0.0, filter.getOutput().getValue(0), 0.0000000001);
    }
}