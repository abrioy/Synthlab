package fr.synthlab.model.module.vcf;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FilterLowPass;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;

public class ModuleVCFLPImpl extends ModuleVCF implements ModuleVCFLP {
    /**
     * JSyn lowPass Filter.
     */
    private FilterLowPass lpFilter = new FilterLowPass();

    public ModuleVCFLPImpl(final Synthesizer synthesizer) {
        super(synthesizer);

        synthesizer.add(lpFilter);
        input = new InputPort("in", this, lpFilter.input);
        output = new OutputPort("out", this, lpFilter.output);

        ports.add(input);
        ports.add(output);

        setF0(f0);
        setResonance(1);
    }

    @Override
    public final void start() {
        super.start();
        lpFilter.start();
    }

    @Override
    public final void stop() {
        super.stop();
        lpFilter.stop();
    }

    @Override
    public final void setF0(final double f0) {
        super.setF0(f0);
        if (fmInput.getConnected() == null) {
            lpFilter.frequency.set(f0);
        }
    }

    @Override
    public void update() {
        filterFm.output.disconnectAll();
        if (fmInput.getConnected() == null) {
            lpFilter.frequency.set(f0);
        } else {
            filterFm.output.connect(lpFilter.frequency);
        }
    }

    @Override
    public final ModuleType getType() {
        return ModuleType.VCFLP;
    }

    @Override
    public final double getResonance() {
        return lpFilter.Q.get();
    }

    @Override
    public final void setResonance(final double value) {
        lpFilter.Q.set(value);
    }

}
