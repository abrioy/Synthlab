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

    /**
     * test on generate
     */
    @Test
    public void testGenerate() {
        filter.getInput().set(50.0);
        filter.generate(0,1);

        assertEquals(50.0, filter.getOutput().getValue(0), 0.000001);
    }

    /**
     * test on generate
     */
    @Test
    public void testGenerate2() {
        filter.setAttenuation(5.0);
        filter.getInput().set(50.0);
        filter.generate(0,1);

        assertEquals(AudioMath.decibelsToAmplitude(5.0)*50.0, filter.getOutput().getValue(0), 0.0000000001);
    }

    /**
     * test on generate
     */
    @Test
    public void testGenerate3() {
        filter.setAttenuation(5.0);
        filter.getInput().set(50.0);
        filter.generate(1,0);

        assertEquals(0.0, filter.getOutput().getValue(0), 0.0000000001);
    }
}