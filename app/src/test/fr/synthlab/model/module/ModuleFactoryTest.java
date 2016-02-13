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

public class ModuleFactoryTest extends TestCase {

    public void testCreateModuleVCOA() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.VCOA) instanceof ModuleVCOA);
    }

    public void testCreateModuleSCOP() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.SCOP) instanceof ModuleSCOP);
    }

    public void testCreateModuleOUT() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.OUT) instanceof ModuleOUT);
    }

    public void testCreateModuleVCA() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.VCA) instanceof ModuleVCA);
    }

    public void testCreateModuleREP() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.REP) instanceof ModuleREP);
    }

    public void testCreateModuleEG() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.EG) instanceof ModuleEG);
    }

    public void testCreateModuleVCFLP() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.VCFLP) instanceof ModuleVCFLP);
    }

    public void testCreateModuleVCFHP() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.VCFHP) instanceof ModuleVCFHP);
    }

    public void testCreateModuleKEYB() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.KEYB) instanceof ModuleKEYB);
    }

    public void testCreateModuleMIX() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.MIX) instanceof ModuleMIX);
    }

    public void testCreateModuleBRUI() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.BRUI) instanceof ModuleBRUI);
    }

    public void testCreateModuleSEQ() throws Exception {
        assertTrue(ModuleFactory.createModule(ModuleType.SEQ) instanceof ModuleSEQ);
    }
}