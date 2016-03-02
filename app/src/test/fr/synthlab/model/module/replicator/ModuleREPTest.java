package fr.synthlab.model.module.replicator;

import com.jsyn.JSyn;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * Tests on the replicator module.
 */
public class ModuleREPTest {

    /**
     * Module tested.
     */
    private ModuleREP moduleREP;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        moduleREP = new ModuleREPImpl(JSyn.createSynthesizer());
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

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort(){
        Port p = moduleREP.getPort("in");
        assertEquals("in", p.getName());
        assertEquals(moduleREP, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort2(){
        Port p = moduleREP.getPort("out1");
        assertEquals("out1", p.getName());
        assertEquals(moduleREP, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort3(){
        Port p = moduleREP.getPort("out2");
        assertEquals("out2", p.getName());
        assertEquals(moduleREP, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort4(){
        Port p = moduleREP.getPort("out3");
        assertEquals("out3", p.getName());
        assertEquals(moduleREP, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort5(){
        assertNull(moduleREP.getPort(""));
    }
}