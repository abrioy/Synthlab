package fr.synthlab.model.module.keyboard;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.ModuleType;
import junit.framework.TestCase;
import org.junit.Before;

/**
 * Tests on the keyboard module.
 */
public class ModuleKEYBTest extends TestCase {

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
        moduleKEYB = new ModuleKEYB(synthesizer);
    }

    /**
     * test on get ports
     */
    public void testGetPorts() {
        assertEquals(2, moduleKEYB.getPorts().size());
    }

    /**
     * test on get type.
     */
    public void testGetType() {
        assertEquals(ModuleType.KEYB, moduleKEYB.getType());
    }

    /**
     * Test on the octave's setter.
     */
    public void testOctave() {
        assertEquals(3,moduleKEYB.getOctave());
    }

    /**
     * Test on the octave change
     */
    public void testChangeOctave1() {
        moduleKEYB.changeOctave(7);
        assertEquals(7,moduleKEYB.getOctave());
    }

    /**
     * Test on the octave change
     */
    public void testChangeOctave2() {
        moduleKEYB.changeOctave(0);
        assertEquals(0,moduleKEYB.getOctave());
    }

    /**
     * Test on the octave change
     */
    public void testChangeOctave3() {
        moduleKEYB.changeOctave(-5);
        assertEquals(0,moduleKEYB.getOctave());
    }

    /**
     * Test on the octave change
     */
    public void testChangeOctave4() {
        moduleKEYB.changeOctave(10);
        assertEquals(7,moduleKEYB.getOctave());
    }
}