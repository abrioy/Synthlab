package fr.synthlab.model.module.vcf;


import com.jsyn.Synthesizer;
import fr.synthlab.model.filter.FilterFm;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public abstract class ModuleVCF implements Module {
    private static final Logger LOGGER
            = Logger.getLogger(ModuleVCF.class.getName());

    protected Collection<Port> ports = new ArrayList<>();
    protected InputPort fmInput;
    protected InputPort input;
    protected OutputPort output;
    /**
     * Fréquence de coupure f0.
     */
    protected double f0 = 440;

    /**
     * Filter modulator.
     */
    protected FilterFm filterFm;

    public ModuleVCF(final Synthesizer synthesizer) {
        filterFm = new FilterFm(f0);

        synthesizer.add(filterFm);

        fmInput = new InputPort("fm", this, filterFm.input);
        ports.add(fmInput);
    }

    public final Collection<Port> getPorts() {
        return ports;
    }

    public void start() {
        filterFm.start();
    }

    public void stop() {
        filterFm.stop();
    }

    public void update() {
    }

    public final double getF0() {
        return f0;
    }

    public void setF0(final double newF0) {
        f0 = newF0;
        filterFm.setF0(f0);
    }
}
