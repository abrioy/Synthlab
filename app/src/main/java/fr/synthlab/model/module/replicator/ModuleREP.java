package fr.synthlab.model.module.replicator;

import com.jsyn.unitgen.PassThrough;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class ModuleREP implements Module {
    private static final Logger logger = Logger.getLogger(ModuleOscilloscope.class.getName());

    /**
     * All ports
     */
    private ArrayList<Port> ports = new ArrayList<>();

    /**
     * Constructor
     */
    public ModuleREP() {
        PassThrough pt = new PassThrough();

        InputPort in = new InputPort("in", this, pt.input);
        OutputPort out1 = new OutputPort("out1", this, pt.output);
        OutputPort out2 = new OutputPort("out2", this, pt.output);
        OutputPort out3 = new OutputPort("out3", this, pt.output);
        ports.add(in);
        ports.add(out1);
        ports.add(out2);
        ports.add(out3);

    }

    /**
     * Getter on ports input and output.
     * @return Scope port
     */
    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    /**
     * Inherit method.
     */
    @Override
    public void start() {

    }

    /**
     * Inherit method.
     */
    @Override
    public void stop() {

    }

    /**
     * Inherit method.
     */
    @Override
    public void update() {

    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.REP;
    }

}
