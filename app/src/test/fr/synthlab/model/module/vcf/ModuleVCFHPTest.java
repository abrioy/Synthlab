package fr.synthlab.model.module.vcf;

import com.jsyn.JSyn;
import fr.synthlab.model.module.ModuleType;
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
     */
    @Before
    public void setUp() {
        moduleVCFHP = new ModuleVCFHPImpl(JSyn.createSynthesizer());
        moduleVCFHP.start();
    }

    /**
     * test setFrequency method
     */
    @Test
    public void testSetFrequency() {
        moduleVCFHP.setF0(5);
        assertEquals(5, moduleVCFHP.getF0(), 0.001);
    }

    /**
     * test getPorts method
     */
    @Test
    public void testGetPorts() {
        Collection<Port> ports = moduleVCFHP.getPorts();
        for (Port p : ports) {
            assertEquals(true, p.getName().equals("fm") || p.getName().equals("in") || p.getName().equals("out"));
        }
    }

    /**
     * test on get type.
     */
    @Test
    public void testGetType() {
        assertEquals(ModuleType.VCFHP, moduleVCFHP.getType());
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
