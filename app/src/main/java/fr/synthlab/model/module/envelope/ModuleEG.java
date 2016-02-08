package fr.synthlab.model.module.envelope;

import com.jsyn.unitgen.EnvelopeDAHDSR;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;

public class ModuleEG implements Module {

    /**
     * All ports
     */
    private ArrayList<Port> ports = new ArrayList<>();

    EnvelopeDAHDSR envelope;

    public ModuleEG() {
        envelope = new EnvelopeDAHDSR();

        InputPort in = new InputPort("in", this, envelope.input);
        ports.add(in);
        OutputPort out = new OutputPort("out", this, envelope.output);
        ports.add(out);

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
        return "EG ADSR";
    }

    public void setAttack(double attack){

    }

    public void setDecay(double decay){

    }

    public void setSustain(double sustain){

    }

    public void setRelease(double release){

    }
}
