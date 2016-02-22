package fr.synthlab.model.module.vcf;

import com.jsyn.JSyn;
import fr.synthlab.model.module.port.Port;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Tests on the Voltage Control Filter High Pass
 */
public class ModuleVCFHPTest {

    /**
     * Module tested
     */
    private ModuleVCFHP moduleVCFHP;

    /**
     * Initialize.
     * @throws Exception
     */
    @Before
    public void setUp() {
        moduleVCFHP = new ModuleVCFHP(JSyn.createSynthesizer());
        moduleVCFHP.start();
    }

    /**
     * test setFrequency method
     *
     * @throws Exception
     */
    @Test
    public void testSetFrequency() throws Exception {
        moduleVCFHP.setF0(5);
        assertEquals(5, moduleVCFHP.getF0(), 0.001);
    }

    /**
     * test getPorts method
     *
     * @throws Exception
     */
    @Test
    public void testGetPorts() throws Exception {
        Collection<Port> ports = moduleVCFHP.getPorts();
        for (Port p : ports) {
            assertEquals(true, p.getName().equals("fm") || p.getName().equals("in") || p.getName().equals("out"));
        }
    }

    /**
     * After execution
     */
    @After
    public void tearDown() {
        moduleVCFHP.stop();
        moduleVCFHP = null;
    }
}
