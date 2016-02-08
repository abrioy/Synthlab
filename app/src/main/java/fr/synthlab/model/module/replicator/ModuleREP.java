package fr.synthlab.model.module.replicator;

import com.jsyn.unitgen.PassThrough;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;

public class ModuleREP implements Module {


    private ArrayList<Port> ports = new ArrayList<>();

    public ModuleREP() {

        PassThrough pt = new PassThrough();

        InputPort in = new InputPort("in", this, pt.input);
        OutputPort out1 = new OutputPort("out1", this, pt.output);
        OutputPort out2 = new OutputPort("out2", this, pt.output);
        OutputPort out3 = new OutputPort("out3", this, pt.output);
    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void update() {

    }

    @Override
    public String getName() {
        return "REP";
    }
}
