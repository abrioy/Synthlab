package fr.synthlab.model.module.sequencer;

import com.jsyn.JSyn;
import fr.synthlab.model.module.ModuleType;
import junit.framework.TestCase;
import org.junit.Before;

/**
 * Tests on the sequencer module.
 */
public class ModuleSEQTest extends TestCase {

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
    public void testGetPorts() {
        assertEquals(2, moduleSEQ.getPorts().size());
    }

    /**
     * test on get type
     */
    public void testGetType() {
        assertEquals(ModuleType.SEQ, moduleSEQ.getType());
    }

    /**
     * test on get current
     */
    public void testGetCurrent() {
        assertEquals(0, moduleSEQ.getCurrent());
    }

    /**
     * test on reset
     */
    public void testReset() {
        moduleSEQ.reset();
        assertEquals(0, moduleSEQ.getCurrent());
    }
}