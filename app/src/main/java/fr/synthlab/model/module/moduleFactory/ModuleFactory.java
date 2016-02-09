package fr.synthlab.model.module.moduleFactory;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.envelope.ModuleEG;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.out.ModuleOut;
import fr.synthlab.model.module.replicator.ModuleREP;
import fr.synthlab.model.module.vca.ModuleVCA;
import fr.synthlab.model.module.vcflp.ModuleVCFLP;
import fr.synthlab.model.module.vcoa.ModuleVCOA;

import java.util.logging.Logger;


public class ModuleFactory {
	private static final Logger logger = Logger.getLogger(ModuleFactory.class.getName());

	/**
     * JSyn Synthesizer
     */
    private static Synthesizer syn = JSyn.createSynthesizer();

    public static Module createModule(ModuleEnum type){
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

    /**
     * @return a new VCO
     */
    private static ModuleVCOA createVCO() {
        return new ModuleVCOA(syn);
    }

    /**
     * @return a new Oscilloscope
     */
    private static ModuleOscilloscope createOscilloscope() {
        return new ModuleOscilloscope(syn);
    }

    /**
     * @return a new ModuleOut
     */
    private static ModuleOut createOut() {
        return new ModuleOut(syn);
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
        return new ModuleEG();
    }

	/**
	 * @return a new ModuleVCFLP
	 */
	private static ModuleVCFLP createVCFLP() {
		return new ModuleVCFLP(syn);
	}



    public static Synthesizer getSyn() {
        return syn;
    }

	public static void startSyn() {
		syn.start();
	}
}

