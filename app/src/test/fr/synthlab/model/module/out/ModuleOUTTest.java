package fr.synthlab.model.module.out;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.softsynth.math.AudioMath;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test on ModuleOUT
 * @see ModuleOUTImpl
 * @author johan
 */
public class ModuleOUTTest {

    /**
     * Module tested
     */
    private ModuleOUT moduleOut;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        Synthesizer synthesizer = JSyn.createSynthesizer();
        moduleOut = new ModuleOUTImpl(synthesizer);
    }

    /**
     * test if mute is active on creation
     */
    @Test
    public void testIsMute() {
        assertFalse(moduleOut.isMute());
    }

    /**
     * set mute to true
     */
    @Test
    public void testSetMuteTrue() {
        moduleOut.setMute(true);
        assertTrue(moduleOut.isMute());
    }

    /**
     * set mute to false
     */
    @Test
    public void testSetMuteFalse() {
        moduleOut.setMute(false);
        assertFalse(moduleOut.isMute());
    }

    /**
     * test on initial value
     */
    @Test
    public void testGetAttenuation() {
        assertEquals(1, moduleOut.getAttenuation(), 0.000000001);
    }

    /**
     * test modification
     */
    @Test
    public void testSetAttenuation() {
        moduleOut.setAttenuation(2);
        assertEquals(AudioMath.decibelsToAmplitude(2), moduleOut.getAttenuation(), 0.000000001);
    }

    /**
     * test on get ports.
     */
    @Test
    public void testLengthGetPort(){
        assertEquals(3,moduleOut.getPorts().size());
    }

    /**
     * test on get type.
     */
    @Test
    public void testGetType() {
        assertEquals(ModuleType.OUT, moduleOut.getType());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort(){
        Port p = moduleOut.getPort("in");
        assertEquals("in", p.getName());
        assertEquals(moduleOut, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort2(){
        Port p = moduleOut.getPort("Right");
        assertEquals("Right", p.getName());
        assertEquals(moduleOut, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort3(){
        Port p = moduleOut.getPort("Left");
        assertEquals("Left", p.getName());
        assertEquals(moduleOut, p.getModule());
        assertFalse(p.isConnected());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort4(){
        assertNull(moduleOut.getPort(""));
    }

    /**
     * test is recording.
     */
    @Test
    public void testIsRecording() {
        assertFalse(moduleOut.isRecording());
    }
}