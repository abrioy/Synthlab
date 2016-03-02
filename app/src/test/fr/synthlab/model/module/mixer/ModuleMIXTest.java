package fr.synthlab.model.module.mixer;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.softsynth.math.AudioMath;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * Tests on the mixer module.
 */
public class ModuleMIXTest {

    /**
     * Module tested.
     */
    private ModuleMIX moduleMIX;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        Synthesizer synth = JSyn.createSynthesizer();
        moduleMIX = new ModuleMIXImpl(synth);
    }

    /**
     * test on get ports
     */
    @Test
    public void testGetPorts() {
        assertEquals(5, moduleMIX.getPorts().size());
    }

    /**
     * test on get type.
     */
    @Test
    public void testGetType() {
        assertEquals(ModuleType.MIX, moduleMIX.getType());
    }

    /**
     * test on the getter of attenuation 1
     */
    @Test
    public void testGetAttenuation1() {
        assertEquals(1.0, moduleMIX.getAttenuation1(), 0.0000);
    }

    /**
     * test on the getter of attenuation 2
     */
    @Test
    public void testGetAttenuation2() {
        assertEquals(1.0, moduleMIX.getAttenuation2(), 0.000000001);
    }

    /**
     * test on the getter of attenuation 3
     */
    @Test
    public void testGetAttenuation3() {
        assertEquals(1.0, moduleMIX.getAttenuation3(), 0.000000001);
    }

    /**
     * test on the getter of attenuation 4
     */
    @Test
    public void testGetAttenuation4() {
        assertEquals(1.0, moduleMIX.getAttenuation4(), 0.000000001);
    }

    /**
     * test on the setter of attenuation 1
     */
    @Test
    public void testSetAttenuation1() {
        moduleMIX.setAttenuation1(5);
        double expected_res = AudioMath.decibelsToAmplitude(5);
        assertEquals(expected_res, moduleMIX.getAttenuation1(), 0.00000000000001);
    }

    /**
     * test on the setter of attenuation 1 with a negative value.
     */
    @Test
    public void testSetAttenuation1_2() {
        moduleMIX.setAttenuation1(-5);
        double expected_res = AudioMath.decibelsToAmplitude(-5);
        assertEquals(expected_res, moduleMIX.getAttenuation1(), 0.00000000000001);
    }

    /**
     * test on the setter of attenuation 2
     */
    @Test
    public void testSetAttenuation2() {
        moduleMIX.setAttenuation2(5);
        double expected_res = AudioMath.decibelsToAmplitude(5);
        assertEquals(expected_res, moduleMIX.getAttenuation2(), 0.00000000000001);
    }

    /**
     * test on the setter of attenuation 2 with a negative value.
     */
    @Test
    public void testSetAttenuation2_2() {
        moduleMIX.setAttenuation2(-5);
        double expected_res = AudioMath.decibelsToAmplitude(-5);
        assertEquals(expected_res, moduleMIX.getAttenuation2(), 0.00000000000001);
    }

    /**
     * test on the setter of attenuation 3
     */
    @Test
    public void testSetAttenuation3() {
        moduleMIX.setAttenuation3(5);
        double expected_res = AudioMath.decibelsToAmplitude(5);
        assertEquals(expected_res, moduleMIX.getAttenuation3(), 0.00000000000001);
    }

    /**
     * test on the setter of attenuation 3 with a negative value.
     */
    @Test
    public void testSetAttenuation3_2() {
        moduleMIX.setAttenuation3(-5);
        double expected_res = AudioMath.decibelsToAmplitude(-5);
        assertEquals(expected_res, moduleMIX.getAttenuation3(), 0.00000000000001);
    }

    /**
     * test on the setter of attenuation 4 with a negative value.
     */
    @Test
    public void testSetAttenuation4() {
        moduleMIX.setAttenuation4(5);
        double expected_res = AudioMath.decibelsToAmplitude(5);
        assertEquals(expected_res, moduleMIX.getAttenuation4(), 0.00000000000001);
    }

    /**
     * test on the setter of attenuation 4
     */
    @Test
    public void testSetAttenuation4_2() {
        moduleMIX.setAttenuation4(-5);
        double expected_res = AudioMath.decibelsToAmplitude(-5);
        assertEquals(expected_res, moduleMIX.getAttenuation4(), 0.00000000000001);
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort(){
        Port p = moduleMIX.getPort("in1");
        assertEquals("in1", p.getName());
        assertEquals(moduleMIX, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort2(){
        Port p = moduleMIX.getPort("in2");
        assertEquals("in2", p.getName());
        assertEquals(moduleMIX, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort3(){
        Port p = moduleMIX.getPort("in3");
        assertEquals("in3", p.getName());
        assertEquals(moduleMIX, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort4(){
        Port p = moduleMIX.getPort("in4");
        assertEquals("in4", p.getName());
        assertEquals(moduleMIX, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort5(){
        Port p = moduleMIX.getPort("out");
        assertEquals("out", p.getName());
        assertEquals(moduleMIX, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort6(){
        assertNull(moduleMIX.getPort(""));
    }
}