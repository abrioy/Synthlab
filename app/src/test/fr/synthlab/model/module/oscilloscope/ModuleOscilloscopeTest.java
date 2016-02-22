package fr.synthlab.model.module.oscilloscope;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import junit.framework.TestCase;
import org.junit.Before;

public class ModuleOscilloscopeTest extends TestCase {

    private ModuleSCOP moduleOsc;

    @Before
    public void setUp() {
        Synthesizer synth = JSyn.createSynthesizer();
        moduleOsc = new ModuleSCOP(synth);
    }

    public void testGetPorts() {
        assertEquals(2, moduleOsc.getPorts().size());
    }
}