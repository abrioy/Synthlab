package fr.synthlab.model.module;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.envelope.ModuleEGImpl;
import fr.synthlab.model.module.keyboard.ModuleKEYB;
import fr.synthlab.model.module.keyboard.ModuleKEYBImpl;
import fr.synthlab.model.module.mixer.ModuleMIXImpl;
import fr.synthlab.model.module.oscilloscope.ModuleSCOP;
import fr.synthlab.model.module.oscilloscope.ModuleSCOPImpl;
import fr.synthlab.model.module.out.ModuleOUT;
import fr.synthlab.model.module.out.ModuleOUTImpl;
import fr.synthlab.model.module.replicator.ModuleREPImpl;
import fr.synthlab.model.module.sequencer.ModuleSEQ;
import fr.synthlab.model.module.sequencer.ModuleSEQImpl;
import fr.synthlab.model.module.vca.ModuleVCA;
import fr.synthlab.model.module.vca.ModuleVCAImpl;
import fr.synthlab.model.module.vcf.ModuleVCFHPImpl;
import fr.synthlab.model.module.vcf.ModuleVCFLP;
import fr.synthlab.model.module.vcf.ModuleVCFLPImpl;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import fr.synthlab.model.module.vcoa.ModuleVCOAImpl;
import fr.synthlab.model.module.whiteNoise.ModuleBRUIImpl;

import java.util.logging.Logger;

/**
 * Module Factory.
 * Create module.
 */
public class ModuleFactory {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(ModuleFactory.class.getName());

    /**
     * JSyn Synthesizer.
     */
    private static Synthesizer syn = JSyn.createSynthesizer();

    /**
     * create module.
     * @param type of module create
     * @return new module
     */
    public static Module createModule(final ModuleType type) {
        Module module = null;
        switch (type) {
            case VCOA:
                module = createVCO();
                break;
            case SCOP:
                module = createOscilloscope();
                break;
            case OUT:
                module = createOut();
                break;
            case VCA:
                module = createVCA();
                break;
            case REP:
                module = createREP();
                break;
            case EG:
                module = createEG();
                break;
            case VCFLP:
                module = createVCFLP();
                break;
            case VCFHP:
                module = createVCFHP();
                break;
            case KEYB:
                module = createKEYB();
                break;
            case MIX:
                module = createMix();
                break;
            case BRUI:
                module = createNoise();
                break;
            case SEQ:
                module = createSEQ();
                break;
            default:
                break;
        }
        if (module != null) {
            LOGGER.finer("Module created: " + module.toString());
            module.start();
        } else {
            LOGGER.severe("Unrecognised module type \""
                    + type.toString() + "\".");
        }
        return module;
    }

    /**
     * @return a new mix module
     */
    private static Module createMix() {
        return new ModuleMIXImpl(syn);
    }

    /**
     * @return a new white noise module
     */
    private static Module createNoise() {
        return new ModuleBRUIImpl(syn);
    }

    /**
     * @return a new VCO
     */
    private static ModuleVCOA createVCO() {
        return new ModuleVCOAImpl(syn);
    }

    /**
     * @return a new Oscilloscope
     */
    private static ModuleSCOP createOscilloscope() {
        return new ModuleSCOPImpl(syn);
    }

    /**
     * @return a new ModuleOUT
     */
    private static ModuleOUT createOut() {
        return new ModuleOUTImpl(syn);
    }

    /**
     * @return a new ModuleVCA
     */
    private static ModuleVCA createVCA() {
        return new ModuleVCAImpl(syn);
    }

    /**
     * @return a new ModuleREP
     */
    private static Module createREP() {
        return new ModuleREPImpl(syn);
    }

    /**
     * @return a new ModuleEG
     */
    private static Module createEG() {
        return new ModuleEGImpl(syn);
    }

    /**
     * @return a new ModuleVCFLP
     */
    private static ModuleVCFLP createVCFLP() {
        return new ModuleVCFLPImpl(syn);
    }

    /**
     * @return a new ModuleVCHP
     */
    private static Module createVCFHP() {
        return new ModuleVCFHPImpl(syn);
    }


    /**
     * @return a new ModuleKEYB
     */
    private static ModuleKEYB createKEYB() {
        return new ModuleKEYBImpl(syn);
    }

    /**
     * @return a new ModuleSEQ
     */
    private static ModuleSEQ createSEQ() {
        return new ModuleSEQImpl(syn);
    }

    /**
     * @return the synthesizer
     */
    public static Synthesizer getSyn() {
        return syn;
    }

    /**
     * start synthesizer.
     */
    public static void startSyn() {
        syn.start();
    }
}
