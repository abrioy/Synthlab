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
     */
    @Before
    public void setUp() {
        moduleVCFLP = new ModuleVCFLP(JSyn.createSynthesizer());
        moduleVCFLP.start();
    }

    /**
     * test setFrequency method
     *
     */
    @Test
    public void testSetFrequency() {
        moduleVCFLP.setF0(5);
        assertEquals(5, moduleVCFLP.getF0(), 0.001);
    }

    /**
     * test setResonance method
     *
     */
    @Test
    public void testSetResonance() {
        moduleVCFLP.setResonance(12);
        assertEquals(12, moduleVCFLP.getResonance(), 0.001);
    }

    /**
     * test getPorts method
     *
     */
    @Test
    public void testGetPorts() {
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
