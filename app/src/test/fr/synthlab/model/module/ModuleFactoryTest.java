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
     * @throws Exception
     */
    public void testCreateModuleVCOA() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.VCOA) instanceof ModuleVCOA);
    }

    /**
     * create a SCOP module.
     * @throws Exception
     */
    public void testCreateModuleSCOP() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.SCOP) instanceof ModuleSCOP);
    }

    /**
     * create a OUT module.
     * @throws Exception
     */
    public void testCreateModuleOUT() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.OUT) instanceof ModuleOUT);
    }

    /**
     * create a VCA module.
     * @throws Exception
     */
    public void testCreateModuleVCA() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.VCA) instanceof ModuleVCA);
    }

    /**
     * create a REP module.
     * @throws Exception
     */
    public void testCreateModuleREP() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.REP) instanceof ModuleREP);
    }

    /**
     * create a EG module
     * @throws Exception
     */
    public void testCreateModuleEG() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.EG) instanceof ModuleEG);
    }

    /**
     * create a VCFLP module.
     * @throws Exception
     */
    public void testCreateModuleVCFLP() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.VCFLP) instanceof ModuleVCFLP);
    }

    /**
     * create a VCFHP module
     * @throws Exception
     */
    public void testCreateModuleVCFHP() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.VCFHP) instanceof ModuleVCFHP);
    }

    /**
     * create a KEYB module
     * @throws Exception
     */
    public void testCreateModuleKEYB() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.KEYB) instanceof ModuleKEYB);
    }

    /**
     * create a MIX module
     * @throws Exception
     */
    public void testCreateModuleMIX() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.MIX) instanceof ModuleMIX);
    }

    /**
     * create a BRUI module
     * @throws Exception
     */
    public void testCreateModuleBRUI() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.BRUI) instanceof ModuleBRUI);
    }

    /**
     * create a SEQ module.
     * @throws Exception
     */
    public void testCreateModuleSEQ() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.SEQ) instanceof ModuleSEQ);
    }
}