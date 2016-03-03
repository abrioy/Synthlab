package fr.synthlab.model.module.keyboard;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests on the keyboard module.
 */
public class ModuleKEYBTest {

    /**
     * Module tested.
     */
    private ModuleKEYB moduleKEYB;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        Synthesizer synthesizer = JSyn.createSynthesizer();
        moduleKEYB = new ModuleKEYBImpl(synthesizer);
    }

    /**
     * test on get ports
     */
    @Test
    public void testGetPorts() {
        assertEquals(2, moduleKEYB.getPorts().size());
    }

    /**
     * test on get type.
     */
    @Test
    public void testGetType() {
        assertEquals(ModuleType.KEYB, moduleKEYB.getType());
    }

    /**
     * Test on the octave's setter.
     */
    @Test
    public void testOctave() {
        assertEquals(3,moduleKEYB.getOctave());
    }

    /**
     * Test on the octave change
     */
    @Test
    public void testChangeOctave1() {
        moduleKEYB.changeOctave(7);
        assertEquals(7,moduleKEYB.getOctave());
    }

    /**
     * Test on the octave change
     */
    @Test
    public void testChangeOctave2() {
        moduleKEYB.changeOctave(0);
        assertEquals(0,moduleKEYB.getOctave());
    }

    /**
     * Test on the octave change
     */
    @Test
    public void testChangeOctave3() {
        moduleKEYB.changeOctave(-5);
        assertEquals(0,moduleKEYB.getOctave());
    }

    /**
     * Test on the octave change
     */
    @Test
    public void testChangeOctave4() {
        moduleKEYB.changeOctave(10);
        assertEquals(7,moduleKEYB.getOctave());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort(){
        Port p = moduleKEYB.getPort("gate");
        assertEquals("gate", p.getName());
        assertEquals(moduleKEYB, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort2(){
        Port p = moduleKEYB.getPort("out");
        assertEquals("out", p.getName());
        assertEquals(moduleKEYB, p.getModule());
        assertFalse(p.isConnected());
    }
}