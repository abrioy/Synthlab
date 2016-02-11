package fr.synthlab.model.module;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.envelope.ModuleEG;
import fr.synthlab.model.module.keyboard.ModuleKEYB;
import fr.synthlab.model.module.mixer.ModuleMIX;
import fr.synthlab.model.module.oscilloscope.ModuleSCOP;
import fr.synthlab.model.module.out.ModuleOUT;
import fr.synthlab.model.module.replicator.ModuleREP;
import fr.synthlab.model.module.vca.ModuleVCA;
import fr.synthlab.model.module.vcf.ModuleVCFHP;
import fr.synthlab.model.module.vcf.ModuleVCFLP;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import fr.synthlab.model.module.whiteNoise.ModuleBRUI;

import java.util.logging.Logger;


public class ModuleFactory {
	private static final Logger logger = Logger.getLogger(ModuleFactory.class.getName());

	/**
     * JSyn Synthesizer
     */
    private static Synthesizer syn = JSyn.createSynthesizer();

    public static Module createModule(ModuleType type){
        Module module = null;
        switch(type){
            case VCOA: module = createVCO();
                break;
            case SCOP: module = createOscilloscope();
                break;
			case OUT: module = createOut();
				break;
            case VCA: module = createVCA();
                break;
            case REP : module = createREP();
                break;
            case EG : module = createEG();
                break;
			case VCFLP : module = createVCFLP();
				break;

            case VCFHP : module = createVCFHP();
                break;
            case KEYB : module = createKEYB();
                break;
            case MIX : module = createMix();
                break;
            case BRUI: module = createNoise();
                break;
        }
		if(module != null){
			logger.finer("Module created: "+module.toString());
			module.start();
		}
		else{
			logger.severe("Unrecognised module type \""+type.toString()+"\".");
		}
        return module;
    }

    private static Module createMix() {
        return new ModuleMIX(syn);
    }

    private static Module createNoise() {
        return new ModuleBRUI(syn);
    }

    /**
     * @return a new VCO
     */
    private static ModuleVCOA createVCO() {
        return new ModuleVCOA(syn);
    }

    /**
     * @return a new Oscilloscope
     */
    private static ModuleSCOP createOscilloscope() {
        return new ModuleSCOP(syn);
    }

    /**
     * @return a new ModuleOUT
     */
    private static ModuleOUT createOut() {
        return new ModuleOUT(syn);
    }

    /**
     * @return a new ModuleVCA
     */
    private static ModuleVCA createVCA() {
        return new ModuleVCA(syn);
    }

    /**
     * @return a new ModuleREP
     */
    private static Module createREP() {
        return new ModuleREP();
    }

    /**
     * @return a new ModuleEG
     */
    private static Module createEG() {
        return new ModuleEG(syn);
    }

	/**
	 * @return a new ModuleVCFLP
	 */
	private static ModuleVCFLP createVCFLP() {
		return new ModuleVCFLP(syn);
	}

    /**
     * @return a new ModuleVCHP
     */
    private static Module createVCFHP() {
        return new ModuleVCFHP(syn);
    }


    /**
     * @return a new ModuleKEYB
     */
    private static ModuleKEYB createKEYB() {
        return new ModuleKEYB(syn);
    }

    public static Synthesizer getSyn() {
        return syn;
    }

	public static void startSyn() {
		syn.start();
	}

}

