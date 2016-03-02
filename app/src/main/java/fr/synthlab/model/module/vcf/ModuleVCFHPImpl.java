package fr.synthlab.model.module.vcf;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FilterHighPass;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;

public class ModuleVCFHPImpl extends ModuleVCF implements ModuleVCFHP {

    /**
     * JSyn highPass Filter.
     */
    private FilterHighPass hpFilter = new FilterHighPass();

    public ModuleVCFHPImpl(final Synthesizer synthesizer) {
        super(synthesizer);
        synthesizer.add(hpFilter);
        input = new InputPort("in", this, hpFilter.input);
        output = new OutputPort("out", this, hpFilter.output);

        ports.add(input);
        ports.add(output);
        setF0(f0);
    }

    @Override
    public final void start() {
        super.start();
        hpFilter.start();
    }

    @Override
    public final void stop() {
        super.stop();
        hpFilter.stop();
    }

    @Override
    public final void setF0(final double f0) {
        super.setF0(f0);
        if (fmInput.getConnected() == null) {
            hpFilter.frequency.set(f0);
        }
    }


    @Override
    public final void update() {
        filterFm.output.disconnectAll();
        if (fmInput.getConnected() == null) {
            hpFilter.frequency.set(f0);
        } else {
            filterFm.output.connect(hpFilter.frequency);
        }
    }

    @Override
    public final ModuleType getType() {
        return ModuleType.VCFHP;
    }
}
