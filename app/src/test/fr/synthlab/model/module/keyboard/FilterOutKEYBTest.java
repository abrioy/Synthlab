package fr.synthlab.model.module.keyboard;

import com.jsyn.ports.ConnectableOutput;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests on the keyboard filter.
 */
public class FilterOutKEYBTest {

    /**
     * Filter tested
     */
    private FilterOutKEYB filterOutKEYB;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        filterOutKEYB = new FilterOutKEYB();
    }

    /**
     * test generate after initialize. Test filter output value.
     */
    @Test
    public void testGenerate() {
        filterOutKEYB.generate(0,1);

        assertEquals(0, filterOutKEYB.getGate().getValue(), 0.00000001);
    }

    /**
     * test generate after set tension. Test filter output value.
     */
    @Test
    public void testGenerate2() {
        filterOutKEYB.setTension(5);
        filterOutKEYB.generate(0,1);

        assertEquals(5, filterOutKEYB.getGate().getValue(), 0.00000001);
    }

    /**
     * test generate after set tension. Test filter output value.
     */
    @Test
    public void testGenerate3() {
        filterOutKEYB.setTension(-5);
        filterOutKEYB.generate(0,1);

        assertEquals(-5, filterOutKEYB.getGate().getValue(), 0.00000001);
    }

    /**
     * Test the getter of the gate port.
     */
    @Test
    public void testGetGate() {
        assertNotNull(filterOutKEYB.getGate());
        assertTrue(filterOutKEYB.getGate() instanceof ConnectableOutput);
    }

    /**
     * test number of port of the generator.
     */
    @Test
    public void testGetPort() {
        assertEquals(1, filterOutKEYB.getPorts().size());
    }
}