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

/**
 * module VCF.
 */
public abstract class ModuleVCF implements Module {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(ModuleVCF.class.getName());

    /**
     * List of ports.
     */
    protected Collection<Port> ports = new ArrayList<>();

    /**
     * port fm.
     */
    protected InputPort fmInput;
    /**
     * input port.
     */
    protected InputPort input;
    /**
     * output port.
     */
    protected OutputPort output;
    /**
     * Fréquence de coupure f0.
     */
    protected double f0 = 440;

    /**
     * Filter modulator.
     */
    protected FilterFm filterFm;

    /**
     * Constructor.
     * @param synthesizer jsyn
     */
    public ModuleVCF(final Synthesizer synthesizer) {
        filterFm = new FilterFm(f0);

        synthesizer.add(filterFm);

        fmInput = new InputPort("fm", this, filterFm.input);
        ports.add(fmInput);
    }

    @Override
    public final Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public void start() {
        filterFm.start();
    }

    @Override
    public void stop() {
        filterFm.stop();
    }

    @Override
    public void update() {
    }

    /**
     * @return current f0
     */
    public final double getF0() {
        return f0;
    }

    /**
     * setter on f0.
     * @param newF0 to set in f0
     */
    public void setF0(final double newF0) {
        f0 = newF0;
        filterFm.setF0(f0);
    }
}
