package fr.synthlab.model.module.sequencer;

import com.jsyn.JSyn;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * Tests on the sequencer module.
 */
public class ModuleSEQTest {

    /**
     * Module tested
     */
    private ModuleSEQ moduleSEQ;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        moduleSEQ = new ModuleSEQImpl(JSyn.createSynthesizer());
    }

    /**
     * test on get ports
     */
    @Test
    public void testGetPorts() {
        assertEquals(2, moduleSEQ.getPorts().size());
    }

    /**
     * test on get type
     */
    @Test
    public void testGetType() {
        assertEquals(ModuleType.SEQ, moduleSEQ.getType());
    }

    /**
     * test on get current
     */
    @Test
    public void testGetCurrent() {
        assertEquals(0, moduleSEQ.getCurrent());
    }

    /**
     * test on reset
     */
    @Test
    public void testReset() {
        moduleSEQ.reset();
        assertEquals(0, moduleSEQ.getCurrent());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort(){
        Port p = moduleSEQ.getPort("gate");
        assertEquals("gate", p.getName());
        assertEquals(moduleSEQ, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort2(){
        Port p = moduleSEQ.getPort("out");
        assertEquals("out", p.getName());
        assertEquals(moduleSEQ, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort3(){
        assertNull(moduleSEQ.getPort(""));
    }
}