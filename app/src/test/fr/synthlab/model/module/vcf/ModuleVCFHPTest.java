package fr.synthlab.model.module.vcf;

import com.jsyn.JSyn;
import fr.synthlab.model.module.port.Port;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class ModuleVCFHPTest {

    private ModuleVCFHP moduleVCFHP;

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

    @After
    public void tearDown() {
        moduleVCFHP.stop();
        moduleVCFHP = null;
    }
}
