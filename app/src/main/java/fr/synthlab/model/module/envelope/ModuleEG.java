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

        InputPort gate = new InputPort("gate", this, envelope.input);
        ports.add(gate);
        OutputPort out = new OutputPort("out", this, envelope.output);
        ports.add(out);

        envelope.attack.setup(0,1,10.0);
        envelope.decay.setup(0,1,10.0);
        envelope.sustain.setup(0,0,12.0);
        envelope.release.setup(0,1,10.0);

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
        envelope.attack.set(attack);
    }

    public void setDecay(double decay){
        envelope.decay.set(decay);
    }

    public void setSustain(double sustain){
        envelope.sustain.set(sustain);
    }

    public void setRelease(double release){
        envelope.release.set(release);
    }
}
