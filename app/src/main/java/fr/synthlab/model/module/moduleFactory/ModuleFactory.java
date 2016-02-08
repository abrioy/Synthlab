package fr.synthlab.model.module.moduleFactory;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.out.ModuleOut;
import fr.synthlab.model.module.replicator.ModuleREP;
import fr.synthlab.model.module.vcoa.ModuleVCOA;

import java.util.logging.Logger;


public class ModuleFactory {
	private static final Logger logger = Logger.getLogger(ModuleFactory.class.getName());

	/**
     * JSyn Synthesizer
     */
    private static Synthesizer syn = JSyn.createSynthesizer();

    public static Module createModule(ModuleEnum module){
        Module m;
        switch(module){
            case VCOA: m = createVCO();
                break;
            case SCOP: m = createOscilloscope();
                break;
            case REP : m = createREP();
                break;
            default : m = createOut(); //OUT
        }
		m.start();
		logger.finer("Module created: "+m.toString());
        return m;
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
     * @return a new ModuleREP
     */
    private static Module createREP() {
        return new ModuleREP();
    }

    public static Synthesizer getSyn() {
        return syn;
    }

	public static void startSyn() {
		syn.start();
	}
}

