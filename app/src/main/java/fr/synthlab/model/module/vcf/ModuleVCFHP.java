package fr.synthlab.model.module.vcf;


import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FilterHighPass;
import fr.synthlab.model.filter.VcoFm;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class ModuleVCFHP implements Module {

    private static final Logger logger = Logger.getLogger(ModuleVCFHP.class.getName());

    private Collection<Port> ports = new ArrayList<>();

    /**
     * Fréquence de coupure f0
     */
    private double f0 = 450;

    /**
     * Filter modulator
     */
    private VcoFm fmFilter;

    private FilterHighPass hpFilter = new FilterHighPass();

    private InputPort input;
    private InputPort fmInput;
    private OutputPort output;

    public ModuleVCFHP(Synthesizer synthesizer){
        fmFilter = new VcoFm(f0);

        synthesizer.add(fmFilter);
        synthesizer.add(hpFilter);

        input = new InputPort("in", this, hpFilter.input);
        fmInput = new InputPort("fm", this, fmFilter.input);
        output = new OutputPort("out", this, hpFilter.output);

        ports.add(input);
        ports.add(fmInput);
        ports.add(output);

    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public void start() {
        fmFilter.start();
        hpFilter.start();
    }

    @Override
    public void stop() {
        fmFilter.stop();
        hpFilter.stop();
    }

    @Override
    public void update() {

    }

    @Override
    public String getName() {
        return null;
    }

    public double getF0() {
        return f0;


    }

    public void setF0(double f0) {
        this.f0 = f0;
        fmFilter.setf0(f0);
        if (fmInput.getConnected() == null) {
            hpFilter.frequency.set(f0);
        }
    }
}
