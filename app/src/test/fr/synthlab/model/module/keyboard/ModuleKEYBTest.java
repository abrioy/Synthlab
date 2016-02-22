package fr.synthlab.model.module.keyboard;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.ModuleType;
import junit.framework.TestCase;
import org.junit.Before;

public class ModuleKEYBTest extends TestCase {

    private ModuleKEYB moduleKEYB;

    @Before
    public void setUp() {
        Synthesizer synthesizer = JSyn.createSynthesizer();
        moduleKEYB = new ModuleKEYB(synthesizer);
    }

    public void testGetPorts() {
        assertEquals(2, moduleKEYB.getPorts().size());
    }

    public void testGetType() {
        assertEquals(ModuleType.KEYB, moduleKEYB.getType());
    }

    public void testOctave() {
        assertEquals(3,moduleKEYB.getOctave());
    }

    public void testChangeOctave1() {
        moduleKEYB.changeOctave(7);
        assertEquals(7,moduleKEYB.getOctave());
    }

    public void testChangeOctave2() {
        moduleKEYB.changeOctave(0);
        assertEquals(0,moduleKEYB.getOctave());
    }

    public void testChangeOctave3() {
        moduleKEYB.changeOctave(-5);
        assertEquals(0,moduleKEYB.getOctave());
    }

    public void testChangeOctave4() {
        moduleKEYB.changeOctave(10);
        assertEquals(7,moduleKEYB.getOctave());
    }
}