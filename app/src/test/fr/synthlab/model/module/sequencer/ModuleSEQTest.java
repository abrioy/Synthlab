package fr.synthlab.model.module.sequencer;

import com.jsyn.JSyn;
import fr.synthlab.model.module.ModuleType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        moduleSEQ = new ModuleSEQ(JSyn.createSynthesizer());
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
}