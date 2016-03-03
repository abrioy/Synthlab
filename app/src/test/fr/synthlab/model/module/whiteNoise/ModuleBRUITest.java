package fr.synthlab.model.module.whiteNoise;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test on white noise module.
 */
public class ModuleBRUITest {

    /**
     * Module tested
     */
    private ModuleBRUIImpl moduleBRUI;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        Synthesizer synth = JSyn.createSynthesizer();
        moduleBRUI = new ModuleBRUIImpl(synth);
    }

    /**
     * test on get ports.
     */
    @Test
    public void testGetPorts() {
        assertEquals(1, moduleBRUI.getPorts().size());
    }

    /**
     * test on get type.
     */
    @Test
    public void testGetType() {
        assertEquals(ModuleType.BRUI, moduleBRUI.getType());
    }

    /**
     * test get port by name.
     */
    @Test
    public void testGetPort(){
        Port p = moduleBRUI.getPort("out");
        assertEquals("out", p.getName());
        assertEquals(moduleBRUI, p.getModule());
        assertFalse(p.isConnected());
    }
}