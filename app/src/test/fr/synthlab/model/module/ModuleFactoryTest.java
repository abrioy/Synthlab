package fr.synthlab.model.module;

import fr.synthlab.model.module.envelope.ModuleEGImpl;
import fr.synthlab.model.module.keyboard.ModuleKEYBImpl;
import fr.synthlab.model.module.mixer.ModuleMIXImpl;
import fr.synthlab.model.module.oscilloscope.ModuleSCOPImpl;
import fr.synthlab.model.module.out.ModuleOUTImpl;
import fr.synthlab.model.module.replicator.ModuleREPImpl;
import fr.synthlab.model.module.sequencer.ModuleSEQImpl;
import fr.synthlab.model.module.vca.ModuleVCAImpl;
import fr.synthlab.model.module.vcf.ModuleVCFHPImpl;
import fr.synthlab.model.module.vcf.ModuleVCFLPImpl;
import fr.synthlab.model.module.vcoa.ModuleVCOAImpl;
import fr.synthlab.model.module.whiteNoise.ModuleBRUIImpl;
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
        assertTrue(ModuleFactory.createModule(ModuleType.VCOA) instanceof ModuleVCOAImpl);
    }

    /**
     * create a SCOP module.
     *
     */
    @Test
    public void testCreateModuleSCOP() {
        assertTrue(ModuleFactory.createModule(ModuleType.SCOP) instanceof ModuleSCOPImpl);
    }

    /**
     * create a OUT module.
     *
     */
    @Test
    public void testCreateModuleOUT() {
        assertTrue(ModuleFactory.createModule(ModuleType.OUT) instanceof ModuleOUTImpl);
    }

    /**
     * create a VCA module.
     *
     */
    @Test
    public void testCreateModuleVCA() {
        assertTrue(ModuleFactory.createModule(ModuleType.VCA) instanceof ModuleVCAImpl);
    }

    /**
     * create a REP module.
     *
     */
    @Test
    public void testCreateModuleREP() {
        assertTrue(ModuleFactory.createModule(ModuleType.REP) instanceof ModuleREPImpl);
    }

    /**
     * create a EG module
     *
     */
    @Test
    public void testCreateModuleEG() {
        assertTrue(ModuleFactory.createModule(ModuleType.EG) instanceof ModuleEGImpl);
    }

    /**
     * create a VCFLP module.
     *
     */
    @Test
    public void testCreateModuleVCFLP() {
        assertTrue(ModuleFactory.createModule(ModuleType.VCFLP) instanceof ModuleVCFLPImpl);
    }

    /**
     * create a VCFHP module
     *
     */
    @Test
    public void testCreateModuleVCFHP() {
        assertTrue(ModuleFactory.createModule(ModuleType.VCFHP) instanceof ModuleVCFHPImpl);
    }

    /**
     * create a KEYB module
     *
     */
    @Test
    public void testCreateModuleKEYB() {
        assertTrue(ModuleFactory.createModule(ModuleType.KEYB) instanceof ModuleKEYBImpl);
    }

    /**
     * create a MIX module
     *
     */
    @Test
    public void testCreateModuleMIX() {
        assertTrue(ModuleFactory.createModule(ModuleType.MIX) instanceof ModuleMIXImpl);
    }

    /**
     * create a BRUI module
     *
     */
    @Test
    public void testCreateModuleBRUI() {
        assertTrue(ModuleFactory.createModule(ModuleType.BRUI) instanceof ModuleBRUIImpl);
    }

    /**
     * create a SEQ module.
     *
     */
    @Test
    public void testCreateModuleSEQ() {
        assertTrue(ModuleFactory.createModule(ModuleType.SEQ) instanceof ModuleSEQImpl);
    }
}