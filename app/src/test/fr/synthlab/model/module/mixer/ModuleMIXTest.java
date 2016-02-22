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
    public void setUp() throws Exception {
        Synthesizer synth = JSyn.createSynthesizer();
        moduleMIX = new ModuleMIX(synth);
    }

    @Test
    public void testGetPorts() throws Exception {
        assertEquals(5, moduleMIX.getPorts().size());
    }

    @Test
    public void testGetType() throws Exception {
        assertEquals(ModuleType.MIX, moduleMIX.getType());
    }

    @Test
    public void testGetAttenuation1() throws Exception {
        assertEquals(1.0, moduleMIX.getAttenuation1());
    }

    @Test
    public void testGetAttenuation2() throws Exception {
        assertEquals(1.0, moduleMIX.getAttenuation2());
    }

    @Test
    public void testGetAttenuation3() throws Exception {
        assertEquals(1.0, moduleMIX.getAttenuation3());
    }

    @Test
    public void testGetAttenuation4() throws Exception {
        assertEquals(1.0, moduleMIX.getAttenuation4());
    }

    @Test
    public void testSetAttenuation1() throws Exception {
        moduleMIX.setAttenuation1(5);
        double expected_res = AudioMath.decibelsToAmplitude(5);
        assertEquals(expected_res, moduleMIX.getAttenuation1(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation1_2() throws Exception {
        moduleMIX.setAttenuation1(-5);
        double expected_res = AudioMath.decibelsToAmplitude(-5);
        assertEquals(expected_res, moduleMIX.getAttenuation1(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation2() throws Exception {
        moduleMIX.setAttenuation2(5);
        double expected_res = AudioMath.decibelsToAmplitude(5);
        assertEquals(expected_res, moduleMIX.getAttenuation2(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation2_2() throws Exception {
        moduleMIX.setAttenuation2(-5);
        double expected_res = AudioMath.decibelsToAmplitude(-5);
        assertEquals(expected_res, moduleMIX.getAttenuation2(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation3() throws Exception {
        moduleMIX.setAttenuation3(5);
        double expected_res = AudioMath.decibelsToAmplitude(5);
        assertEquals(expected_res, moduleMIX.getAttenuation3(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation3_2() throws Exception {
        moduleMIX.setAttenuation3(-5);
        double expected_res = AudioMath.decibelsToAmplitude(-5);
        assertEquals(expected_res, moduleMIX.getAttenuation3(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation4() throws Exception {
        moduleMIX.setAttenuation4(5);
        double expected_res = AudioMath.decibelsToAmplitude(5);
        assertEquals(expected_res, moduleMIX.getAttenuation4(), 0.00000000000001);
    }

    @Test
    public void testSetAttenuation4_2() throws Exception {
        moduleMIX.setAttenuation4(-5);
        double expected_res = AudioMath.decibelsToAmplitude(-5);
        assertEquals(expected_res, moduleMIX.getAttenuation4(), 0.00000000000001);
    }
}