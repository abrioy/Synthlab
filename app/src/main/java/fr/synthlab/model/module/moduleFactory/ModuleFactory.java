package fr.synthlab.model.module.moduleFactory;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.out.ModuleOut;
import fr.synthlab.model.module.vcoa.ModuleVCOA;


public class ModuleFactory {
    /**
     * JSyn Synthesizer
     */
    private static Synthesizer syn = JSyn.createSynthesizer();

    /**
     * @return a new VCO
     */
    public static ModuleVCOA createVCO() {
        return new ModuleVCOA(syn);
    }

    /**
     * @return a new Oscilloscope
     */
    public static ModuleOscilloscope createOscilloscope() {
        return new ModuleOscilloscope(syn);
    }

    /**
     * @return a new ModuleOut
     */
    public static ModuleOut createOut() {
        return new ModuleOut(syn);
    }

    public static Synthesizer getSyn() {
        return syn;
    }
}

