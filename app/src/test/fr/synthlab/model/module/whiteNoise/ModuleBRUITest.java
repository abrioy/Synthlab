package fr.synthlab.model.module.whiteNoise;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.ModuleType;
import junit.framework.TestCase;

/**
 * Test on white noise module.
 */
public class ModuleBRUITest extends TestCase {

    /**
     * Module tested
     */
    private ModuleBRUI moduleBRUI;

    /**
     * Initialize.
     */
    public void setUp() {
        Synthesizer synth = JSyn.createSynthesizer();
        moduleBRUI = new ModuleBRUI(synth);
    }

    /**
     * test on get ports.
     */
    public void testGetPorts() {
        assertEquals(1, moduleBRUI.getPorts().size());
    }

    /**
     * test on get type.
     */
    public void testGetType() {
        assertEquals(ModuleType.BRUI, moduleBRUI.getType());
    }
}