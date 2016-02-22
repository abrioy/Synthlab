package fr.synthlab.model.module.whiteNoise;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.ModuleType;
import junit.framework.TestCase;

public class ModuleBRUITest extends TestCase {

    private ModuleBRUI moduleBRUI;

    public void setUp() throws Exception {
        Synthesizer synth = JSyn.createSynthesizer();
        moduleBRUI = new ModuleBRUI(synth);
    }

    public void testGetPorts() throws Exception {
        assertEquals(1, moduleBRUI.getPorts().size());
    }

    public void testGetType() throws Exception {
        assertEquals(ModuleType.BRUI, moduleBRUI.getType());
    }
}