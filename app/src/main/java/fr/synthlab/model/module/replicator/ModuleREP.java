package fr.synthlab.model.module.replicator;

import com.jsyn.Synthesizer;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.oscilloscope.ModuleSCOP;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class ModuleREP implements Module {
    private static final Logger LOGGER = Logger.getLogger(ModuleSCOP.class.getName());

    /**
     * All ports.
     */
    private ArrayList<Port> ports = new ArrayList<>();

    /**
     * Filter with an input and 3 outputs.
     */
    private FilterREP filterREP;

    /**
     * Constructor.
     * @param synthesizer Synthesizer
     */
    public ModuleREP(Synthesizer synthesizer) {
        filterREP = new FilterREP();

        InputPort in = new InputPort("in", this, filterREP.getIn());
        OutputPort out1 = new OutputPort("out1", this, filterREP.getOut1());
        OutputPort out2 = new OutputPort("out2", this, filterREP.getOut2());
        OutputPort out3 = new OutputPort("out3", this, filterREP.getOut3());

        ports.add(in);
        ports.add(out1);
        ports.add(out2);
        ports.add(out3);
        synthesizer.add(filterREP);
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
     * Start module.
     */
    @Override
    public void start() {
        filterREP.start();
    }

    /**
     * Stop module.
     */
    @Override
    public void stop() {
        filterREP.stop();
    }

    /**
     * Inherit method.
     */
    @Override
    public void update() {

    }

    @Override
    public ModuleType getType() {
        return ModuleType.REP;
    }

}
