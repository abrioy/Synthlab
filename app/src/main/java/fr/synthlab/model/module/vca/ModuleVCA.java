package fr.synthlab.model.module.vca;

import com.jsyn.Synthesizer;
import fr.synthlab.model.filter.FilterAttenuator;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;

public class ModuleVCA implements Module {

    private Collection<Port> ports = new ArrayList<>();

    private InputPort inputPort, inputPortAm;
    private OutputPort outputPort;

    private FilterAttenuator filterAttenuator = new FilterAttenuator();

    public ModuleVCA(Synthesizer synthesizer) {
        inputPort = new InputPort("in", this, filterAttenuator.input);
        inputPortAm = new InputPort("am", this, null/*TODO*/);
        outputPort = new OutputPort("out", this, null/*TODO*/);

        ports.add(inputPort);
        ports.add(inputPortAm);
        ports.add(outputPort);
    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public void start() {
        filterAttenuator.start();
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
        return "VCA";
    }
}
