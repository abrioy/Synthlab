package fr.synthlab.model.filter;

import com.jsyn.ports.UnitOutputPort;
import fr.synthlab.model.module.keyboard.FilterKEYB;
import junit.framework.TestCase;
import org.junit.Before;

public class KeyboardFilterTest extends TestCase {

    private FilterKEYB keyboardFilter;

    @Before
    public void setUp() throws Exception {
        keyboardFilter = new FilterKEYB();
    }

    public void testInitializeTension() throws Exception {
        assertEquals(0.0, keyboardFilter.getTension());
    }

    public void testPressKey() throws Exception {
        keyboardFilter.pressKey();
        assertEquals(5.0, keyboardFilter.getTension());
    }

    public void testPressKey2() throws Exception {
        keyboardFilter.pressKey();
        keyboardFilter.releaseKey();
        keyboardFilter.pressKey();
        assertEquals(5.0, keyboardFilter.getTension());
    }

    public void testReleaseKey() throws Exception {
        keyboardFilter.releaseKey();
        assertEquals(-5.0, keyboardFilter.getTension());
    }

    public void testReleaseKey2() throws Exception {
        keyboardFilter.pressKey();
        keyboardFilter.releaseKey();
        assertEquals(-5.0, keyboardFilter.getTension());
    }

    public void testGetGate() throws Exception {
        assertTrue(keyboardFilter.getGate() instanceof UnitOutputPort);
    }
}