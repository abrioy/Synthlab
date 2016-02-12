package fr.synthlab.model.module.keyboard;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.ModuleType;
import junit.framework.TestCase;
import org.junit.Before;

public class ModuleKEYBTest extends TestCase {

    private ModuleKEYB moduleKEYB;

    @Before
    public void setUp() throws Exception {
        Synthesizer synthesizer = JSyn.createSynthesizer();
        moduleKEYB = new ModuleKEYB(synthesizer);
    }

    public void testGetPorts() throws Exception {
        assertEquals(2, moduleKEYB.getPorts().size());
    }

    public void testGetType() throws Exception {
        assertEquals(ModuleType.KEYB, moduleKEYB.getType());
    }

    public void testOctave() throws Exception {
        assertEquals(3,moduleKEYB.getOctave());
    }

    public void testChangeOctave1() throws Exception {
        moduleKEYB.changeOctave(7);
        assertEquals(7,moduleKEYB.getOctave());
    }

    public void testChangeOctave2() throws Exception {
        moduleKEYB.changeOctave(0);
        assertEquals(0,moduleKEYB.getOctave());
    }

    public void testChangeOctave3() throws Exception {
        moduleKEYB.changeOctave(-5);
        assertEquals(0,moduleKEYB.getOctave());
    }

    public void testChangeOctave4() throws Exception {
        moduleKEYB.changeOctave(10);
        assertEquals(7,moduleKEYB.getOctave());
    }
}