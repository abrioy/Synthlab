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
import junit.framework.TestCase;

/**
 * Tests on the ModuleFactory
 */
public class ModuleFactoryTest extends TestCase {

    /**
     * create a VCOA module.
     *
     */
    public void testCreateModuleVCOA() {
        assertTrue(ModuleFactory.createModule(ModuleType.VCOA) instanceof ModuleVCOA);
    }

    /**
     * create a SCOP module.
     *
     */
    public void testCreateModuleSCOP() {
        assertTrue(ModuleFactory.createModule(ModuleType.SCOP) instanceof ModuleSCOP);
    }

    /**
     * create a OUT module.
     *
     */
    public void testCreateModuleOUT() {
        assertTrue(ModuleFactory.createModule(ModuleType.OUT) instanceof ModuleOUT);
    }

    /**
     * create a VCA module.
     *
     */
    public void testCreateModuleVCA() {
        assertTrue(ModuleFactory.createModule(ModuleType.VCA) instanceof ModuleVCA);
    }

    /**
     * create a REP module.
     *
     */
    public void testCreateModuleREP() {
        assertTrue(ModuleFactory.createModule(ModuleType.REP) instanceof ModuleREP);
    }

    /**
     * create a EG module
     *
     */
    public void testCreateModuleEG() {
        assertTrue(ModuleFactory.createModule(ModuleType.EG) instanceof ModuleEG);
    }

    /**
     * create a VCFLP module.
     *
     */
    public void testCreateModuleVCFLP() {
        assertTrue(ModuleFactory.createModule(ModuleType.VCFLP) instanceof ModuleVCFLP);
    }

    /**
     * create a VCFHP module
     *
     */
    public void testCreateModuleVCFHP() {
        assertTrue(ModuleFactory.createModule(ModuleType.VCFHP) instanceof ModuleVCFHP);
    }

    /**
     * create a KEYB module
     *
     */
    public void testCreateModuleKEYB() {
        assertTrue(ModuleFactory.createModule(ModuleType.KEYB) instanceof ModuleKEYB);
    }

    /**
     * create a MIX module
     *
     */
    public void testCreateModuleMIX() {
        assertTrue(ModuleFactory.createModule(ModuleType.MIX) instanceof ModuleMIX);
    }

    /**
     * create a BRUI module
     *
     */
    public void testCreateModuleBRUI() {
        assertTrue(ModuleFactory.createModule(ModuleType.BRUI) instanceof ModuleBRUI);
    }

    /**
     * create a SEQ module.
     *
     */
    public void testCreateModuleSEQ() {
        assertTrue(ModuleFactory.createModule(ModuleType.SEQ) instanceof ModuleSEQ);
    }
}