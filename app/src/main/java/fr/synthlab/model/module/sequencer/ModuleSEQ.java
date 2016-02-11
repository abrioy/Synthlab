package fr.synthlab.model.module.sequencer;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.PassThrough;
import fr.synthlab.model.filter.SEQFilter;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class ModuleSEQ implements Module {
    private static final Logger logger = Logger.getLogger(ModuleSEQ.class.getName());

    /**
     * All ports
     */
    private ArrayList<Port> ports = new ArrayList<>();

    private int step;
    private SEQFilter seqFilter;

    /**
     * Constructor
     */
    public ModuleSEQ(Synthesizer synth) {
        step = 1;

        PassThrough pt = new PassThrough();

        InputPort gate = new InputPort("gate", this, pt.input);
        OutputPort out = new OutputPort("out", this, pt.output);
        ports.add(gate);
        ports.add(out);

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
    public ModuleType getType() {
        return ModuleType.SEQ;
    }

    public void resetStep() {
        step = 1;
    }
}
