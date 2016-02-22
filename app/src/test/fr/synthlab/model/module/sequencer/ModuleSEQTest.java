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
     * @throws Exception
     */
    @Before
    public void setUp() {
        moduleSEQ = new ModuleSEQ(JSyn.createSynthesizer());
    }

    /**
     * test on get ports
     * @throws Exception
     */
    public void testGetPorts() throws Exception {
        assertEquals(2, moduleSEQ.getPorts().size());
    }

    /**
     * test on get type
     * @throws Exception
     */
    public void testGetType() throws Exception {
        assertEquals(ModuleType.SEQ, moduleSEQ.getType());
    }

    /**
     * test on get current
     * @throws Exception
     */
    public void testGetCurrent() throws Exception {
        assertEquals(0, moduleSEQ.getCurrent());
    }

    /**
     * test on reset
     * @throws Exception
     */
    public void testReset() throws Exception {
        moduleSEQ.reset();
        assertEquals(0, moduleSEQ.getCurrent());
    }
}