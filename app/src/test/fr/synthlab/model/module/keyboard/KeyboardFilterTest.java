package fr.synthlab.model.module.keyboard;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    }
}