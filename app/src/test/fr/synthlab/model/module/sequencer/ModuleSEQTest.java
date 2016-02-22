package fr.synthlab.model.module.sequencer;

import com.jsyn.JSyn;
import fr.synthlab.model.module.ModuleType;
import junit.framework.TestCase;
import org.junit.Before;

public class ModuleSEQTest extends TestCase {

    private ModuleSEQ moduleSEQ;

    @Before
    public void setUp() {
        moduleSEQ = new ModuleSEQ(JSyn.createSynthesizer());
    }

    public void testGetPorts() throws Exception {
        assertEquals(2, moduleSEQ.getPorts().size());
    }

    public void testGetType() throws Exception {
        assertEquals(ModuleType.SEQ, moduleSEQ.getType());
    }

    public void testGetCurrent() throws Exception {
        assertEquals(0, moduleSEQ.getCurrent());
    }

    public void testReset() throws Exception {
        moduleSEQ.reset();
        assertEquals(0, moduleSEQ.getCurrent());
    }
}