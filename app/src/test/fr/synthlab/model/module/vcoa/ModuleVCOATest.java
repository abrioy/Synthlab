package fr.synthlab.model.module.vcoa;

import com.jsyn.JSyn;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

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
        moduleVCOA = new ModuleVCOAImpl(JSyn.createSynthesizer());
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
     * test on get type.
     */
    @Test
    public void testGetType() {
        assertEquals(ModuleType.VCOA, moduleVCOA.getType());
    }

    /**
     * test getShape method.
     */
    @Test
    public void testGetShape() {
        assertNull(moduleVCOA.getShape());
    }

    /**
     * test setShape method.
     */
    @Test
    public void testSetShape() {
        moduleVCOA.setShape(ShapeVCOA.TRIANGLE);
        assertEquals(ShapeVCOA.TRIANGLE, moduleVCOA.getShape());
    }

    /**
     * test setShape method.
     */
    @Test
    public void testSetShape2() {
        moduleVCOA.setShape(ShapeVCOA.SAWTOOTH);
        assertEquals(ShapeVCOA.SAWTOOTH, moduleVCOA.getShape());
    }

    /**
     * test setShape method.
     */
    @Test
    public void testSetShape3() {
        moduleVCOA.setShape(ShapeVCOA.SINE);
        assertEquals(ShapeVCOA.SINE, moduleVCOA.getShape());
    }

    /**
     * test setShape method.
     */
    @Test
    public void testSetShape4() {
        moduleVCOA.setShape(ShapeVCOA.SQUARE);
        assertEquals(ShapeVCOA.SQUARE, moduleVCOA.getShape());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort(){
        Port p = moduleVCOA.getPort("fm");
        assertEquals("fm", p.getName());
        assertEquals(moduleVCOA, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort2(){
        Port p = moduleVCOA.getPort("out");
        assertEquals("out", p.getName());
        assertEquals(moduleVCOA, p.getModule());
        assertFalse(p.isConnected());
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
