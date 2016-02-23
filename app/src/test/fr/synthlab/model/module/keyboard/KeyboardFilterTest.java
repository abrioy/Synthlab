package fr.synthlab.model.module.keyboard;

import junit.framework.TestCase;
import org.junit.Before;

/**
 * Tests on the keyboard filter.
 */
public class KeyboardFilterTest extends TestCase {

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
    public void testGetTension() {
        assertEquals(0.0, keyboardFilter.getTension());
    }

    /**
     * Test press key to generate a tension.
     */
    public void testPressKey() {
        keyboardFilter.pressKey();
        assertEquals(5.0, keyboardFilter.getTension());
    }

    /**
     * Test press key to generate a tension.
     */
    public void testPressKey2() {
        keyboardFilter.pressKey();
        keyboardFilter.releaseKey();
        keyboardFilter.pressKey();
        assertEquals(5.0, keyboardFilter.getTension());
    }

    /**
     * Test release key to generate a tension.
     */
    public void testReleaseKey() {
        keyboardFilter.releaseKey();
        assertEquals(-5.0, keyboardFilter.getTension());
    }

    /**
     * Test release key to generate a tension.
     */
    public void testReleaseKey2() {
        keyboardFilter.pressKey();
        keyboardFilter.releaseKey();
        assertEquals(-5.0, keyboardFilter.getTension());
    }

    /**
     * Test the getter of the gate port.
     */
    public void testGetGate() {
        assertNotNull(keyboardFilter.getGate());
    }
}