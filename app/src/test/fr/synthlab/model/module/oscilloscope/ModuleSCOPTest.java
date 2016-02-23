package fr.synthlab.model.module.oscilloscope;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.ModuleType;
import junit.framework.TestCase;
import org.junit.Before;

/**
 * Tests on the oscilloscope module.
 */
public class ModuleSCOPTest extends TestCase {

    /**
     * Module tested.
     */
    private ModuleSCOP moduleOsc;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        Synthesizer synth = JSyn.createSynthesizer();
        moduleOsc = new ModuleSCOP(synth);
    }

    /**
     * test on get ports
     */
    public void testGetPorts() {
        assertEquals(2, moduleOsc.getPorts().size());
    }

    /**
     * test on get type.
     */
    public void testGetType() {
        assertEquals(ModuleType.SCOP, moduleOsc.getType());
    }

    /**
     * test on get scale.
     */
    public void testGetScale() {
        assertEquals(4, moduleOsc.getScale());
    }

    /**
     * test on set scale.
     */
    public void testSetScale1() {
        moduleOsc.setScale(0);
        assertEquals(0, moduleOsc.getScale());
    }

    /**
     * test on set scale.
     */
    public void testSetScale2() {
        moduleOsc.setScale(100);
        assertEquals(100, moduleOsc.getScale());
    }

    /**
     * test on set scale.
     */
    public void testSetScale3() {
        moduleOsc.setScale(-10);
        assertEquals(0, moduleOsc.getScale());
    }

    /**
     * test on set scale.
     */
    public void testSetScale4() {
        moduleOsc.setScale(200);
        assertEquals(100, moduleOsc.getScale());
    }

    /**
     * test on get oscillator JComponent
     */
    public void testGetOscillatorJComponent() {
        assertNotNull(moduleOsc.getOscillatorJComponent());
    }
}