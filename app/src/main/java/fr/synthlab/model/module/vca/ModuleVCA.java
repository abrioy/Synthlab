package fr.synthlab.model.module.vca;

import com.jsyn.Synthesizer;
import fr.synthlab.model.filter.FilterAttenuator;
import fr.synthlab.model.filter.VcaAM;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class ModuleVCA implements Module {
    private static final Logger logger = Logger.getLogger(ModuleVCA.class.getName());

    private Collection<Port> ports = new ArrayList<>();

    private InputPort inputPort, inputPortAm;
    private OutputPort outputPort;

    private FilterAttenuator filterAttenuator = new FilterAttenuator();
    private VcaAM vcaAM;

    private double attenuation = 0.0;

    public ModuleVCA(Synthesizer synthesizer) {
        vcaAM = new VcaAM(filterAttenuator.output);

        synthesizer.add(filterAttenuator);
        synthesizer.add(vcaAM);

        inputPort = new InputPort("in", this, filterAttenuator.input);
        inputPortAm = new InputPort("am", this, vcaAM.input);
        outputPort = new OutputPort("out", this, vcaAM.output);

        ports.add(inputPort);
        ports.add(inputPortAm);
        ports.add(outputPort);
    }

    public void setAttenuation(double attenuation) {
        this.attenuation = attenuation;
        filterAttenuator.setAttenuation(attenuation);
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
    public ModuleEnum getType() {
        return ModuleEnum.VCA;
    }
}
