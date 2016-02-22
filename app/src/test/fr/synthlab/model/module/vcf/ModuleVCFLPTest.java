package fr.synthlab.model.module.vcf;

import com.jsyn.JSyn;
import fr.synthlab.model.module.port.Port;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Tests on the Voltage Control Filter Low Pass
 */
public class ModuleVCFLPTest {

    /**
     * Module tested
     */
    private ModuleVCFLP moduleVCFLP;

    /**
     * Initialize.
     * @throws Exception
     */
    @Before
    public void setUp() {
        moduleVCFLP = new ModuleVCFLP(JSyn.createSynthesizer());
        moduleVCFLP.start();
    }

    /**
     * test setFrequency method
     *
     * @throws Exception
     */
    @Test
    public void testSetFrequency() throws Exception {
        moduleVCFLP.setF0(5);
        assertEquals(5, moduleVCFLP.getF0(), 0.001);
    }

    /**
     * test setResonance method
     *
     * @throws Exception
     */
    @Test
    public void testSetResonance() throws Exception {
        moduleVCFLP.setResonance(12);
        assertEquals(12, moduleVCFLP.getResonance(), 0.001);
    }

    /**
     * test getPorts method
     *
     * @throws Exception
     */
    @Test
    public void testGetPorts() throws Exception {
        Collection<Port> ports = moduleVCFLP.getPorts();
        for (Port p : ports) {
            assertEquals(true, p.getName().equals("fm") || p.getName().equals("in") || p.getName().equals("out"));
        }
    }

    /**
     * After execution
     */
    @After
    public void tearDown() {
        moduleVCFLP.stop();
        moduleVCFLP = null;
    }
}
