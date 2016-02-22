package fr.synthlab.model.module.keyboard;

import junit.framework.TestCase;
import org.junit.Before;

public class KeyboardFilterTest extends TestCase {

    private FilterKEYB keyboardFilter;

    @Before
    public void setUp() {
        keyboardFilter = new FilterKEYB();
    }

    public void testInitializeTension() {
        assertEquals(0.0, keyboardFilter.getTension());
    }

    public void testPressKey() {
        keyboardFilter.pressKey();
        assertEquals(5.0, keyboardFilter.getTension());
    }

    public void testPressKey2() {
        keyboardFilter.pressKey();
        keyboardFilter.releaseKey();
        keyboardFilter.pressKey();
        assertEquals(5.0, keyboardFilter.getTension());
    }

    public void testReleaseKey() {
        keyboardFilter.releaseKey();
        assertEquals(-5.0, keyboardFilter.getTension());
    }

    public void testReleaseKey2() {
        keyboardFilter.pressKey();
        keyboardFilter.releaseKey();
        assertEquals(-5.0, keyboardFilter.getTension());
    }

    public void testGetGate() {
        assertTrue(keyboardFilter.getGate() != null);
    }
}