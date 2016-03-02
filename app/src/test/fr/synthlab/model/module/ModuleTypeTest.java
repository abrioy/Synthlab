package fr.synthlab.model.module;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test module type enumeration.
 */
public class ModuleTypeTest {

    /**
     * test get name from long name.
     */
    @Test
    public void testGetNameFromLong() {
        assertEquals("", ModuleType.getNameFromLong("longName"));
    }
    
    /**
     * test get name from long name.
     */
    @Test
    public void testGetNameFromLong2() {
        assertEquals("VCOA", ModuleType.getNameFromLong("VCO Type A"));
    }

    /**
     * test get name from long name.
     */
    @Test
    public void testGetNameFromLong3() {
        assertEquals("VCA", ModuleType.getNameFromLong("VCA"));
    }

    /**
     * test get name from long name.
     */
    @Test
    public void testGetNameFromLong4() {
        assertEquals("OUT", ModuleType.getNameFromLong("Audio Output"));
    }

    /**
     * test get name from long name.
     */
    @Test
    public void testGetNameFromLong5() {
        assertEquals("SCOP", ModuleType.getNameFromLong("Oscilloscope"));
    }

    /**
     * test get name from long name.
     */
    @Test
    public void testGetNameFromLong6() {
        assertEquals("REP", ModuleType.getNameFromLong("Repeater"));
    }

    /**
     * test get name from long name.
     */
    @Test
    public void testGetNameFromLong7() {
        assertEquals("EG", ModuleType.getNameFromLong("Envelope Generator"));
    }

    /**
     * test get name from long name.
     */
    @Test
    public void testGetNameFromLong8() {
        assertEquals("VCFLP", ModuleType.getNameFromLong("VCF (LP)"));
    }

    /**
     * test get name from long name.
     */
    @Test
    public void testGetNameFromLong9() {
        assertEquals("VCFHP", ModuleType.getNameFromLong("VCF (HP)"));
    }

    /**
     * test get name from long name.
     */
    @Test
    public void testGetNameFromLong10() {
        assertEquals("KEYB", ModuleType.getNameFromLong("Keyboard"));
    }

    /**
     * test get name from long name.
     */
    @Test
    public void testGetNameFromLong11() {
        assertEquals("MIX", ModuleType.getNameFromLong("Mixer"));
    }

    /**
     * test get name from long name.
     */
    @Test
    public void testGetNameFromLong12() {
        assertEquals("BRUI", ModuleType.getNameFromLong("White Noise"));
    }

    /**
     * test get name from long name.
     */
    @Test
    public void testGetNameFromLong13() {
        assertEquals("SEQ", ModuleType.getNameFromLong("Sequencer"));
    }

    /**
     * test get long name.
     */
    @Test
    public void testGetLongName() {
        assertEquals("Sequencer", ModuleType.SEQ.getLongName());
    }
    
    /**
     * test get long name.
     */
    @Test
    public void testGetLongName2() {
        assertEquals("VCO Type A", ModuleType.VCOA.getLongName());
    }

    /**
     * test get long name.
     */
    @Test
    public void testGetLongName3() {
        assertEquals("VCA", ModuleType.VCA.getLongName());
    }

    /**
     * test get long name.
     */
    @Test
    public void testGetLongName4() {
        assertEquals("Audio Output", ModuleType.OUT.getLongName());
    }

    /**
     * test get long name.
     */
    @Test
    public void testGetLongName5() {
        assertEquals("Oscilloscope", ModuleType.SCOP.getLongName());
    }

    /**
     * test get long name.
     */
    @Test
    public void testGetLongName6() {
        assertEquals("Repeater", ModuleType.REP.getLongName());
    }

    /**
     * test get long name.
     */
    @Test
    public void testGetLongName7() {
        assertEquals("Envelope Generator", ModuleType.EG.getLongName());
    }

    /**
     * test get long name.
     */
    @Test
    public void testGetLongName8() {
        assertEquals("VCF (LP)", ModuleType.VCFLP.getLongName());
    }

    /**
     * test get long name.
     */
    @Test
    public void testGetLongName9() {
        assertEquals("VCF (HP)", ModuleType.VCFHP.getLongName());
    }

    /**
     * test get long name.
     */
    @Test
    public void testGetLongName10() {
        assertEquals("Keyboard", ModuleType.KEYB.getLongName());
    }

    /**
     * test get long name.
     */
    @Test
    public void testGetLongName11() {
        assertEquals("Mixer", ModuleType.MIX.getLongName());
    }

    /**
     * test get long name.
     */
    @Test
    public void testGetLongName12() {
        assertEquals("White Noise", ModuleType.BRUI.getLongName());
    }

    /**
     * test to string.
     */
    @Test
    public void testToString() {
        assertEquals("SEQ", ModuleType.SEQ.toString());
    }

    /**
     * test to string.
     */
    @Test
    public void testToString2() {
        assertEquals("VCOA", ModuleType.VCOA.toString());
    }

    /**
     * test to string.
     */
    @Test
    public void testToString3() {
        assertEquals("VCA", ModuleType.VCA.toString());
    }

    /**
     * test to string.
     */
    @Test
    public void testToString4() {
        assertEquals("OUT", ModuleType.OUT.toString());
    }

    /**
     * test to string.
     */
    @Test
    public void testToString5() {
        assertEquals("SCOP", ModuleType.SCOP.toString());
    }

    /**
     * test to string.
     */
    @Test
    public void testToString6() {
        assertEquals("REP", ModuleType.REP.toString());
    }

    /**
     * test to string.
     */
    @Test
    public void testToString7() {
        assertEquals("EG", ModuleType.EG.toString());
    }

    /**
     * test to string.
     */
    @Test
    public void testToString8() {
        assertEquals("VCFLP", ModuleType.VCFLP.toString());
    }

    /**
     * test to string.
     */
    @Test
    public void testToString9() {
        assertEquals("VCFHP", ModuleType.VCFHP.toString());
    }

    /**
     * test to string.
     */
    @Test
    public void testToString10() {
        assertEquals("KEYB", ModuleType.KEYB.toString());
    }

    /**
     * test to string.
     */
    @Test
    public void testToString11() {
        assertEquals("MIX", ModuleType.MIX.toString());
    }

    /**
     * test to string.
     */
    @Test
    public void testToString12() {
        assertEquals("BRUI", ModuleType.BRUI.toString());
    }
}