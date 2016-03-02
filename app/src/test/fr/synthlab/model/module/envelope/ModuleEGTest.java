package fr.synthlab.model.module.envelope;

import com.jsyn.JSyn;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * Tests on the envelope generator module.
 */
public class ModuleEGTest {

    /**
     * Module tested.
     */
    private ModuleEG moduleEG;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        moduleEG = new ModuleEGImpl(JSyn.createSynthesizer());
    }

    /**
     * test on get ports.
     */
    @Test
    public void testGetPorts() {
        assertEquals(2, moduleEG.getPorts().size());
    }

    /**
     * test on get type.
     */
    @Test
    public void testGetType() {
        assertEquals(ModuleType.EG, moduleEG.getType());
    }

    /**
     * Test on attack getter.
     */
    @Test
    public void testGetAttack() {
        assertEquals(1.0, moduleEG.getAttack(), 0.000000001);
    }

    /**
     * Test on decay getter.
     */
    @Test
    public void testGetDecay() {
        assertEquals(1.0, moduleEG.getDecay(), 0.000000001);
    }

    /**
     * Test on sustain getter.
     */
    @Test
    public void testGetSustain() {
        assertEquals(0.5, moduleEG.getSustain(), 0.000000001);
    }

    /**
     * Test on release getter.
     */
    @Test
    public void testGetRelease() {
        assertEquals(1.0, moduleEG.getRelease(), 0.000000001);
    }

    /**
     * Test on attack setter.
     */
    @Test
    public void testSetAttack() {
        moduleEG.setAttack(10.0);
        assertEquals(10.0, moduleEG.getAttack(), 0.000000001);
    }

    /**
     * Test on decay setter.
     */
    @Test
    public void testSetDecay() {
        moduleEG.setDecay(10.0);
        assertEquals(10.0, moduleEG.getDecay(), 0.000000001);
    }

    /**
     * Test on sustain setter.
     */
    @Test
    public void testSetSustain() {
        moduleEG.setSustain(10.0);
        assertEquals(10.0, moduleEG.getSustain(), 0.000000001);
    }

    /**
     * Test on release setter.
     */
    @Test
    public void testSetRelease() {
        moduleEG.setRelease(10.0);
        assertEquals(10.0, moduleEG.getRelease(), 0.000000001);
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort(){
        Port p = moduleEG.getPort("gate");
        assertEquals("gate", p.getName());
        assertEquals(moduleEG, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort2(){
        Port p = moduleEG.getPort("out");
        assertEquals("out", p.getName());
        assertEquals(moduleEG, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort3(){
        assertNull(moduleEG.getPort(""));
    }
}