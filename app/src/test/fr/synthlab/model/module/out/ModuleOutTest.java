package fr.synthlab.model.module.out;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test on ModuleOut
 * @see ModuleOut
 * @author johan
 */
public class ModuleOutTest {

    private ModuleOut moduleOut;

    @Before
    public void setUp() throws Exception {
        Synthesizer synthesizer = JSyn.createSynthesizer();
        moduleOut = new ModuleOut(synthesizer);
    }

    /**
     * test if mute is active on creation
     * @throws Exception
     */
    @Test
    public void testIsMute() throws Exception {
        assertFalse(moduleOut.isMute());
    }

    /**
     * set mute to true
     * @throws Exception
     */
    @Test
    public void testSetMuteTrue() throws Exception {
        moduleOut.setMute(true);
        assertTrue(moduleOut.isMute());
    }

    /**
     * set mute to false
     * @throws Exception
     */
    @Test
    public void testSetMuteFalse() throws Exception {
        moduleOut.setMute(false);
        assertFalse(moduleOut.isMute());
    }

    /**
     * test on initial value
     * @throws Exception
     */
    @Test
    public void testGetAttenuation() throws Exception {
        assertEquals(0,moduleOut.getAttenuation(),0.000000001);
    }

    /**
     * test modification
     * @throws Exception
     */
    @Test
    public void testSetAttenuation() throws Exception {
        moduleOut.setAttenuation(2);
        assertEquals(2,moduleOut.getAttenuation(),0.000000001);
    }

    @Test
    public void testLenthGetPort(){
        assertEquals(3,moduleOut.getPorts().size());
    }
}