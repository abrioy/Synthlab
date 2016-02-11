package fr.synthlab.model.module.vcf;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FilterLowPass;
import fr.synthlab.model.module.ModuleTypes;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;

public class ModuleVCFLP extends ModuleVCF {
    /**
     * JSyn lowPass Filter
     */
    private FilterLowPass lpFilter = new FilterLowPass();

    public ModuleVCFLP(Synthesizer synthesizer) {
        super(synthesizer);

        synthesizer.add(lpFilter);
        input = new InputPort("in", this, lpFilter.input);
        output = new OutputPort("out", this, lpFilter.output);

        ports.add(input);
        ports.add(output);

        setF0(f0);
        setResonance(1);
    }

    /**
     * start the module
     */
    @Override
    public void start() {
        super.start();
        lpFilter.start();
    }

    /**
     * stop the module
     */
    @Override
    public void stop() {
        super.stop();
        lpFilter.stop();
    }

    /**
     * set the cut frequency
     * @param f0 the cut frequency
     */
    @Override
    public void setF0(double f0) {
        super.setF0(f0);
        if (fmInput.getConnected() == null) {
            lpFilter.frequency.set(f0);
        }
    }

    /**
     * this method is called when we connect or disconnect the fm Input port
     * when the fm input port is connected, we connect his output to the LowPass filter
     * when the fm input port is disconnected, we set the frequency f0 to the LowPass filter
     */
    @Override
    public void update() {
        if (fmInput.getConnected() == null) {
            filterFm.output.disconnectAll();
            lpFilter.frequency.set(f0);
        } else {
            filterFm.output.connect(lpFilter.frequency);
        }
    }

    @Override
    public ModuleTypes getType() {
        return ModuleTypes.VCFLP;
    }

    public double getResonance() {
        return lpFilter.Q.get();
    }

    public void setResonance(double value) {
        lpFilter.Q.set(value);
    }

}
