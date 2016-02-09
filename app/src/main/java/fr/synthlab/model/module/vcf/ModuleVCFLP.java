package fr.synthlab.model.module.vcf;

import com.jsyn.Synthesizer;

public class ModuleVCFLP extends ModuleVCF {


    public ModuleVCFLP(Synthesizer synthesizer) {
        super(synthesizer);
    }

    @Override
    public String getName() {
        return "VCFLP";
    }
}
