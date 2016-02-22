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
     * @throws Exception
     */
    public void setUp() throws Exception {
        Synthesizer synth = JSyn.createSynthesizer();
        moduleBRUI = new ModuleBRUI(synth);
    }

    /**
     * test on get ports.
     * @throws Exception
     */
    public void testGetPorts() throws Exception {
        assertEquals(1, moduleBRUI.getPorts().size());
    }

    /**
     * test on get type.
     * @throws Exception
     */
    public void testGetType() throws Exception {
        assertEquals(ModuleType.BRUI, moduleBRUI.getType());
    }
}