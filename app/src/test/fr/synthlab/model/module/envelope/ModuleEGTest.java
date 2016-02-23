package fr.synthlab.model.module.envelope;

import com.jsyn.JSyn;
import fr.synthlab.model.module.ModuleType;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests on the envelope generator module.
 */
public class ModuleEGTest extends TestCase {

    /**
     * Module tested.
     */
    private ModuleEG moduleEG;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        moduleEG = new ModuleEG(JSyn.createSynthesizer());
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
        assertEquals(1.0, moduleEG.getAttack());
    }

    /**
     * Test on decay getter.
     */
    @Test
    public void testGetDecay() {
        assertEquals(1.0, moduleEG.getDecay());
    }

    /**
     * Test on sustain getter.
     */
    @Test
    public void testGetSustain() {
        assertEquals(0.5, moduleEG.getSustain());
    }

    /**
     * Test on release getter.
     */
    @Test
    public void testGetRelease() {
        assertEquals(1.0, moduleEG.getRelease());
    }

    /**
     * Test on attack setter.
     */
    @Test
    public void testSetAttack() {
        moduleEG.setAttack(10.0);
        assertEquals(10.0, moduleEG.getAttack());
    }

    /**
     * Test on decay setter.
     */
    @Test
    public void testSetDecay() {
        moduleEG.setDecay(10.0);
        assertEquals(10.0, moduleEG.getDecay());
    }

    /**
     * Test on sustain setter.
     */
    @Test
    public void testSetSustain() {
        moduleEG.setSustain(10.0);
        assertEquals(10.0, moduleEG.getSustain());
    }

    /**
     * Test on release setter.
     */
    @Test
    public void testSetRelease() {
        moduleEG.setRelease(10.0);
        assertEquals(10.0, moduleEG.getRelease());
    }
}