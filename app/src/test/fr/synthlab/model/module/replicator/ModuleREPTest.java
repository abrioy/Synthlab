package fr.synthlab.model.module.replicator;

import com.jsyn.JSyn;
import fr.synthlab.model.module.ModuleType;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests on the replicator module.
 */
public class ModuleREPTest extends TestCase {

    /**
     * Module tested.
     */
    private ModuleREP moduleREP;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        moduleREP = new ModuleREP(JSyn.createSynthesizer());
    }

    /**
     * test on get ports.
     */
    @Test
    public void testGetPorts() {
         assertEquals(4, moduleREP.getPorts().size());
    }

    /**
     * test on get type.
     */
    @Test
    public void testGetType() {
        assertEquals(ModuleType.REP, moduleREP.getType());
    }
}