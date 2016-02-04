package fr.synthlab.model.module.moduleFactory;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.out.ModuleOut;
import fr.synthlab.model.module.vcoa.ModuleVCOA;


public class ModuleFactory {
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
            default : m = createOut(); //OUT
        }
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

    public static Synthesizer getSyn() {
        return syn;
    }
}

