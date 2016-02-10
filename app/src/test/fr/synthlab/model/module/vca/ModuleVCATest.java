package fr.synthlab.model.module.vca;

import com.jsyn.JSyn;
import fr.synthlab.model.module.port.Port;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class ModuleVCATest {
    private ModuleVCA moduleVCA;

    @Before
    public void setUp() {
        moduleVCA = new ModuleVCA(JSyn.createSynthesizer());
        moduleVCA.start();
    }

    /**
     * test setFrequency method
     *
     * @throws Exception
     */
    @Test
    public void testSetAttenuation() throws Exception {
        moduleVCA.setAttenuation(12);
        assertEquals(12, moduleVCA.getAttenuation(), 0.001);
    }

    /**
     * test getPorts method
     *
     * @throws Exception
     */
    @Test
    public void testGetPorts() throws Exception {
        Collection<Port> ports = moduleVCA.getPorts();
        for (Port p : ports) {
            assertEquals(true, p.getName().equals("am") || p.getName().equals("in") || p.getName().equals("out"));
        }
    }

    @After
    public void tearDown() {
        moduleVCA.stop();
        moduleVCA = null;
    }
}
