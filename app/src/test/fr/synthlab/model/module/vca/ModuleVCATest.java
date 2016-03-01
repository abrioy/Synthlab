package fr.synthlab.model.module.vca;

import com.jsyn.JSyn;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Tests on the Voltage Control Amplifier.
 */
public class ModuleVCATest {

    /**
     * Module tested
     */
    private ModuleVCA moduleVCA;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        moduleVCA = new ModuleVCAImpl(JSyn.createSynthesizer());
        moduleVCA.start();
    }

    /**
     * test setFrequency method
     */
    @Test
    public void testSetAttenuation() {
        moduleVCA.setAttenuation(12);
        assertEquals(12, moduleVCA.getAttenuation(), 0.001);
    }

    /**
     * test getPorts method
     */
    @Test
    public void testGetPorts() {
        Collection<Port> ports = moduleVCA.getPorts();
        for (Port p : ports) {
            assertEquals(true, p.getName().equals("am") || p.getName().equals("in") || p.getName().equals("out"));
        }
    }

    /**
     * test on get type.
     */
    @Test
    public void testGetType() {
        assertEquals(ModuleType.VCA, moduleVCA.getType());
    }

    /**
     * After execution
     */
    @After
    public void tearDown() {
        moduleVCA.stop();
        moduleVCA = null;
    }
}
