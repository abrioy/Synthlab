package fr.synthlab.model.module.oscilloscope;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import junit.framework.TestCase;
import org.junit.Before;

//TODO Javadoc
public class ModuleOscilloscopeTest extends TestCase {

    private ModuleSCOP moduleOsc;

    @Before
    public void setUp() throws Exception {
        Synthesizer synth = JSyn.createSynthesizer();
        moduleOsc = new ModuleSCOP(synth);
    }

    public void testGetPorts() throws Exception {
        assertEquals(2, moduleOsc.getPorts().size());
    }

    //TODO more test ?
}