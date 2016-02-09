package fr.synthlab.model.module.vcflp;

import com.jsyn.Synthesizer;
import fr.synthlab.model.filter.FilterAttenuator;
import fr.synthlab.model.filter.VcaAM;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;

public class ModuleVCFLP implements Module {

    private Collection<Port> ports = new ArrayList<>();

    private InputPort inputPort, inputPortFm;
    private OutputPort outputPort;

    private FilterAttenuator filterAttenuator = new FilterAttenuator();
    private VcaAM vcaAM;

    private double threshold = 0.0;

    public ModuleVCFLP(Synthesizer synthesizer) {
        vcaAM = new VcaAM(filterAttenuator.output);

        synthesizer.add(filterAttenuator);
        synthesizer.add(vcaAM);

        inputPort = new InputPort("in", this, filterAttenuator.input);
        inputPortFm = new InputPort("fm", this, vcaAM.input);
        outputPort = new OutputPort("out", this, vcaAM.output);

        ports.add(inputPort);
        ports.add(inputPortFm);
        ports.add(outputPort);
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
        filterAttenuator.setAttenuation(threshold);
    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public void start() {
        filterAttenuator.start();
        vcaAM.start();
    }

    @Override
    public void stop() {
        filterAttenuator.stop();
    }

    @Override
    public void update() {

    }

    @Override
    public String getName() {
        return "VCFLP";
    }
}
