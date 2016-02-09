package fr.synthlab.model.module.vcf;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FilterLowPass;
import fr.synthlab.model.module.ModuleEnum;
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

        setF0(f0);
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
    public void setF0(double f0) {
        super.setF0(f0);
        if (fmInput.getConnected() == null) {
            lpFilter.frequency.set(f0);
        }
    }

    @Override
    public void update() {
        if (fmInput.getConnected() == null) {
            fmFilter.output.disconnectAll();
            lpFilter.frequency.set(f0);
        } else {
            fmFilter.output.connect(lpFilter.frequency);
        }
    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.VCFLP;
    }

    public void setResonance(double value) {
        lpFilter.Q.set(value);
    }
}
