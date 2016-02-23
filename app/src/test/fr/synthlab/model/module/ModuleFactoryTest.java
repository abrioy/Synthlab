package fr.synthlab.model.module;

import fr.synthlab.model.module.envelope.ModuleEG;
import fr.synthlab.model.module.keyboard.ModuleKEYB;
import fr.synthlab.model.module.mixer.ModuleMIX;
import fr.synthlab.model.module.oscilloscope.ModuleSCOP;
import fr.synthlab.model.module.out.ModuleOUT;
import fr.synthlab.model.module.replicator.ModuleREP;
import fr.synthlab.model.module.sequencer.ModuleSEQ;
import fr.synthlab.model.module.vca.ModuleVCA;
import fr.synthlab.model.module.vcf.ModuleVCFHP;
import fr.synthlab.model.module.vcf.ModuleVCFLP;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import fr.synthlab.model.module.whiteNoise.ModuleBRUI;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests on the ModuleFactory
 */
public class ModuleFactoryTest {

    /**
     * create a VCOA module.
     */
    @Test
    public void testCreateModuleVCOA() {
        assertTrue(ModuleFactory.createModule(ModuleType.VCOA) instanceof ModuleVCOA);
    }

    /**
     * create a SCOP module.
     *
     */
    @Test
    public void testCreateModuleSCOP() {
        assertTrue(ModuleFactory.createModule(ModuleType.SCOP) instanceof ModuleSCOP);
    }

    /**
     * create a OUT module.
     *
     */
    @Test
    public void testCreateModuleOUT() {
        assertTrue(ModuleFactory.createModule(ModuleType.OUT) instanceof ModuleOUT);
    }

    /**
     * create a VCA module.
     *
     */
    @Test
    public void testCreateModuleVCA() {
        assertTrue(ModuleFactory.createModule(ModuleType.VCA) instanceof ModuleVCA);
    }

    /**
     * create a REP module.
     *
     */
    @Test
    public void testCreateModuleREP() {
        assertTrue(ModuleFactory.createModule(ModuleType.REP) instanceof ModuleREP);
    }

    /**
     * create a EG module
     *
     */
    @Test
    public void testCreateModuleEG() {
        assertTrue(ModuleFactory.createModule(ModuleType.EG) instanceof ModuleEG);
    }

    /**
     * create a VCFLP module.
     *
     */
    @Test
    public void testCreateModuleVCFLP() {
        assertTrue(ModuleFactory.createModule(ModuleType.VCFLP) instanceof ModuleVCFLP);
    }

    /**
     * create a VCFHP module
     *
     */
    @Test
    public void testCreateModuleVCFHP() {
        assertTrue(ModuleFactory.createModule(ModuleType.VCFHP) instanceof ModuleVCFHP);
    }

    /**
     * create a KEYB module
     *
     */
    @Test
    public void testCreateModuleKEYB() {
        assertTrue(ModuleFactory.createModule(ModuleType.KEYB) instanceof ModuleKEYB);
    }

    /**
     * create a MIX module
     *
     */
    @Test
    public void testCreateModuleMIX() {
        assertTrue(ModuleFactory.createModule(ModuleType.MIX) instanceof ModuleMIX);
    }

    /**
     * create a BRUI module
     *
     */
    @Test
    public void testCreateModuleBRUI() {
        assertTrue(ModuleFactory.createModule(ModuleType.BRUI) instanceof ModuleBRUI);
    }

    /**
     * create a SEQ module.
     *
     */
    @Test
    public void testCreateModuleSEQ() {
        assertTrue(ModuleFactory.createModule(ModuleType.SEQ) instanceof ModuleSEQ);
    }
}