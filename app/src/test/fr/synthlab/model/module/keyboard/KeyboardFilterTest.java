package fr.synthlab.model.module.keyboard;

import com.jsyn.ports.ConnectableOutput;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests on the keyboard filter.
 */
public class KeyboardFilterTest {

    /**
     * Filter tested
     */
    private FilterKEYB keyboardFilter;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        keyboardFilter = new FilterKEYB();
    }

    /**
     * test on get tension
     */
    @Test
    public void testGetTension() {
        assertEquals(0.0, keyboardFilter.getTension(), 0.000000001);
    }

    /**
     * Test press key to generate a tension.
     */
    @Test
    public void testPressKey() {
        keyboardFilter.pressKey();
        assertEquals(5.0, keyboardFilter.getTension(), 0.000000001);
    }

    /**
     * Test press key to generate a tension.
     */
    @Test
    public void testPressKey2() {
        keyboardFilter.pressKey();
        keyboardFilter.releaseKey();
        keyboardFilter.pressKey();
        assertEquals(5.0, keyboardFilter.getTension(), 0.000000001);
    }

    /**
     * Test release key to generate a tension.
     */
    @Test
    public void testReleaseKey() {
        keyboardFilter.releaseKey();
        assertEquals(-5.0, keyboardFilter.getTension(), 0.000000001);
    }

    /**
     * Test release key to generate a tension.
     */
    @Test
    public void testReleaseKey2() {
        keyboardFilter.pressKey();
        keyboardFilter.releaseKey();
        assertEquals(-5.0, keyboardFilter.getTension(), 0.000000001);
    }

    /**
     * Test the getter of the gate port.
     */
    @Test
    public void testGetGate() {
        assertNotNull(keyboardFilter.getGate());
        assertTrue(keyboardFilter.getGate() instanceof ConnectableOutput);
    }

    /**
     * test generate after initialize. Test filter output value.
     */
    @Test
    public void testGenerate() {
        keyboardFilter.generate(0, 1);

        assertEquals(0, keyboardFilter.getGate().getValue(), 0.00000001);
    }

    /**
     * test generate after press key. Test filter output value.
     */
    @Test
    public void testGenerate2() {
        keyboardFilter.pressKey();
        keyboardFilter.generate(0, 1);

        assertEquals(5, keyboardFilter.getGate().getValue(), 0.00000001);
    }

    /**
     * test generate after release key. Test filter output value.
     */
    @Test
    public void testGenerate3() {
        keyboardFilter.releaseKey();
        keyboardFilter.generate(0, 1);

        assertEquals(-5, keyboardFilter.getGate().getValue(), 0.00000001);
    }

    /**
     * test number of port of the generator.
     */
    @Test
    public void testGetPort() {
        assertEquals(1, keyboardFilter.getPorts().size());
    }
}