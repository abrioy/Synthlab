package fr.synthlab.model.module.replicator;

import com.jsyn.Synthesizer;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class ModuleREPImpl implements ModuleREP {
    private static final Logger LOGGER
            = Logger.getLogger(ModuleREPImpl.class.getName());

    /**
     * All ports.
     */
    private ArrayList<Port> ports = new ArrayList<>();

    /**
     * Filter with an input and 3 outputs.
     */
    private FilterREPImpl filterREP;

    /**
     * Constructor.
     * @param synthesizer Synthesizer
     */
    public ModuleREPImpl(final Synthesizer synthesizer) {
        filterREP = new FilterREPImpl();

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

    @Override
    public final Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public final void start() {
        filterREP.start();
    }

    @Override
    public final void stop() {
        filterREP.stop();
    }

    @Override
    public void update() {
    }

    @Override
    public final ModuleType getType() {
        return ModuleType.REP;
    }
}
