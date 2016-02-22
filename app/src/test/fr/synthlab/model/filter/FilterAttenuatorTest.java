package fr.synthlab.model.filter;

import com.softsynth.math.AudioMath;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * test on FilterAttenuator
 * @author johan
 */
public class FilterAttenuatorTest {

    /**
     * variable tested
     */
    private FilterAttenuator filter;

    /**
     * initialise test
     */
    @Before
    public void setUp(){
        filter = new FilterAttenuator();
    }

    /**
     * test on setter
     */
    @Test
    public void testSetAttenuation() {
        filter.setAttenuation(10);
        assertEquals(AudioMath.decibelsToAmplitude(10), filter.getAttenuation(), 0.00000000000001);
        filter.setAttenuation(13);
        assertEquals(AudioMath.decibelsToAmplitude(13), filter.getAttenuation(), 0.00000000000001);
    }

    /**
     * test on getter
     */
    @Test
    public void testGetAttenuation() {
        assertEquals(1, filter.getAttenuation(), 0.00000000000001);
    }
}