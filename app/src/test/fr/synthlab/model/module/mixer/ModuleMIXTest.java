package fr.synthlab.model.module.mixer;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.softsynth.math.AudioMath;
import fr.synthlab.model.module.ModuleType;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ModuleMIXTest extends TestCase {

    private ModuleMIX moduleMIX;

    @Before
    public void setUp() {
        Synthesizer synth = JSyn.createSynthesizer();
        moduleMIX = new ModuleMIX(synth);
    }

    @Test
    public void testGetPorts() {
        assertEquals(5, moduleMIX.getPorts().size());
    }

    @Test
    public void testGetType() {
        assertEquals(ModuleType.MIX, moduleMIX.getType());
    }

    @Test
    public void testGetAttenuation1() {
        assertEquals(1.0, moduleMIX.getAttenuation1());
    }

    @Test
    public void testGetAttenuation2() {
        assertEquals(1.0, moduleMIX.getAttenuation2());
    }

    @Test
    public void testGetAttenuation3() {
        assertEquals(1.0, moduleMIX.getAttenuation3());
    }

    @Test
    public void testGetAttenuation4() {
        assertEquals(1.0, moduleMIX.getAttenuation4());
    }

    @Test
    public void testSetAttenuation1() {
        moduleMIX.setAttenuation1(5);
        double expected_res = AudioMath.decibelsToAmplitude(5);
        assertEquals(expected_res, moduleMIX.getAttenuation1(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation1_2() {
        moduleMIX.setAttenuation1(-5);
        double expected_res = AudioMath.decibelsToAmplitude(-5);
        assertEquals(expected_res, moduleMIX.getAttenuation1(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation2() {
        moduleMIX.setAttenuation2(5);
        double expected_res = AudioMath.decibelsToAmplitude(5);
        assertEquals(expected_res, moduleMIX.getAttenuation2(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation2_2() {
        moduleMIX.setAttenuation2(-5);
        double expected_res = AudioMath.decibelsToAmplitude(-5);
        assertEquals(expected_res, moduleMIX.getAttenuation2(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation3() {
        moduleMIX.setAttenuation3(5);
        double expected_res = AudioMath.decibelsToAmplitude(5);
        assertEquals(expected_res, moduleMIX.getAttenuation3(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation3_2() {
        moduleMIX.setAttenuation3(-5);
        double expected_res = AudioMath.decibelsToAmplitude(-5);
        assertEquals(expected_res, moduleMIX.getAttenuation3(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation4() {
        moduleMIX.setAttenuation4(5);
        double expected_res = AudioMath.decibelsToAmplitude(5);
        assertEquals(expected_res, moduleMIX.getAttenuation4(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation4_2() {
        moduleMIX.setAttenuation4(-5);
        double expected_res = AudioMath.decibelsToAmplitude(-5);
        assertEquals(expected_res, moduleMIX.getAttenuation4(), 0.00000000000001);
    }
}