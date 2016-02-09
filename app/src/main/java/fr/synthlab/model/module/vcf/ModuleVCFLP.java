package fr.synthlab.model.module.vcf;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FilterLowPass;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;

public class ModuleVCFLP extends ModuleVCF {

    private FilterLowPass lpFilter = new FilterLowPass();

    public ModuleVCFLP(Synthesizer synthesizer) {
        super(synthesizer);

        synthesizer.add(lpFilter);
        input = new InputPort("in", this, lpFilter.input);
        output = new OutputPort("out", this, lpFilter.output);

        ports.add(input);
        ports.add(output);
    }

    @Override
    public void start() {
        super.start();
        lpFilter.start();
    }

    @Override
    public void stop() {
        super.stop();
        lpFilter.stop();
    }

    @Override
    public String getName() {
        return "VCFLP";
    }

    @Override
    public void setF0(double f0) {
        super.setF0(f0);
        if (fmInput.getConnected() == null) {
            lpFilter.frequency.set(f0);
        }
    }
}
