package fr.synthlab.model.module.oscilloscope;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests on the oscilloscope module.
 */
public class ModuleSCOPTest {

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
        moduleOsc = new ModuleSCOPImpl(synth);
    }

    /**
     * test on get ports
     */
    @Test
    public void testGetPorts() {
        assertEquals(2, moduleOsc.getPorts().size());
    }

    /**
     * test on get type.
     */
    @Test
    public void testGetType() {
        assertEquals(ModuleType.SCOP, moduleOsc.getType());
    }

    /**
     * test on get scale.
     */
    @Test
    public void testGetScale() {
        assertEquals(4, moduleOsc.getScale());
    }

    /**
     * test on set scale.
     */
    @Test
    public void testSetScale1() {
        moduleOsc.setScale(0);
        assertEquals(0, moduleOsc.getScale());
    }

    /**
     * test on set scale.
     */
    @Test
    public void testSetScale2() {
        moduleOsc.setScale(100);
        assertEquals(100, moduleOsc.getScale());
    }

    /**
     * test on set scale.
     */
    @Test
    public void testSetScale3() {
        moduleOsc.setScale(-10);
        assertEquals(0, moduleOsc.getScale());
    }

    /**
     * test on set scale.
     */
    @Test
    public void testSetScale4() {
        moduleOsc.setScale(200);
        assertEquals(100, moduleOsc.getScale());
    }

    /**
     * test on get oscillator JComponent
     */
    @Test
    public void testGetOscillatorJComponent() {
        assertNotNull(moduleOsc.getOscillatorJComponent());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort(){
        Port p = moduleOsc.getPort("in");
        assertEquals("in", p.getName());
        assertEquals(moduleOsc, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort2(){
        Port p = moduleOsc.getPort("out");
        assertEquals("out", p.getName());
        assertEquals(moduleOsc, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort3(){
        assertNull(moduleOsc.getPort(""));
    }
}