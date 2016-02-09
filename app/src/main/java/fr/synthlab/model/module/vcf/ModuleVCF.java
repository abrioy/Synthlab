package fr.synthlab.model.module.vcf;


import com.jsyn.Synthesizer;
import fr.synthlab.model.filter.FmFilter;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public abstract class ModuleVCF implements Module {

    private static final Logger logger = Logger.getLogger(ModuleVCF.class.getName());

    protected Collection<Port> ports = new ArrayList<>();
    protected InputPort fmInput;
    protected InputPort input;
    protected OutputPort output;
    /**
     * Fréquence de coupure f0
     */
    protected double f0 = 440;
    /**
     * Filter modulator
     */
    protected FmFilter fmFilter;

    public ModuleVCF(Synthesizer synthesizer) {
        fmFilter = new FmFilter(f0);

        synthesizer.add(fmFilter);

        fmInput = new InputPort("fm", this, fmFilter.input);
        ports.add(fmInput);
    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public void start() {
        fmFilter.start();
    }

    @Override
    public void stop() {
        fmFilter.stop();
    }

    @Override
    public void update() {

    }

    @Override
    public abstract ModuleEnum getType();

    public double getF0() {
        return f0;
    }

    public void setF0(double f0) {
        this.f0 = f0;
        fmFilter.setf0(f0);
    }
}
