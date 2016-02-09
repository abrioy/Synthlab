package fr.synthlab.model.module.vcf;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FilterHighPass;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;

public class ModuleVCFHP extends ModuleVCF {

    private FilterHighPass hpFilter = new FilterHighPass();

    public ModuleVCFHP(Synthesizer synthesizer) {
        super(synthesizer);
        synthesizer.add(hpFilter);
        input = new InputPort("in", this, hpFilter.input);
        output = new OutputPort("out", this, hpFilter.output);

        ports.add(input);
        ports.add(output);
        setF0(f0);
    }

    @Override
    public void start() {
        super.start();
        hpFilter.start();
    }

    @Override
    public void stop() {
        super.stop();
        hpFilter.stop();
    }

    @Override
    public String getName() {
        return "VCFHP";
    }

    @Override
    public void setF0(double f0) {
        super.setF0(f0);
        if (fmInput.getConnected() == null) {
            hpFilter.frequency.set(f0);
        }
    }

    @Override
    public void update() {
        if (fmInput.getConnected() == null) {
            fmFilter.output.disconnectAll();
            hpFilter.frequency.set(f0);
        } else {
            fmFilter.output.connect(hpFilter.frequency);
        }
    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.VCFHP;
    }
}
