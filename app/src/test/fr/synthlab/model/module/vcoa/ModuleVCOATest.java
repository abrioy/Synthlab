package fr.synthlab.model.module.vcoa;

import com.jsyn.JSyn;
import fr.synthlab.model.module.port.Port;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Tests on the Voltage Control Oscillator Type A module.
 */
public class ModuleVCOATest {

    /**
     * Module tested
     */
    private ModuleVCOA moduleVCOA;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        moduleVCOA = new ModuleVCOA(JSyn.createSynthesizer());
        moduleVCOA.start();
    }

    /**
     * test setFrequency method
     *
     */
    @Test
    public void testSetFrequency() {
        moduleVCOA.setFrequency(5);
        assertEquals(5, moduleVCOA.getFrequency(), 0.001);
    }

    /**
     * test getPorts method
     *
     */
    @Test
    public void testGetPorts() {
        Collection<Port> ports = moduleVCOA.getPorts();
        for (Port p : ports) {
            assertEquals(true, p.getName().equals("fm") || p.getName().equals("out"));
        }
    }

    /**
     * After execution.
     */
    @After
    public void tearDown() {
        moduleVCOA.stop();
        moduleVCOA = null;
    }
}
