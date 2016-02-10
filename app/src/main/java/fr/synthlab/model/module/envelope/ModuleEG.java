package fr.synthlab.model.module.envelope;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class ModuleEG implements Module {
    private static final Logger logger = Logger.getLogger(ModuleOscilloscope.class.getName());

    /**
     * All ports
     */
    private ArrayList<Port> ports = new ArrayList<>();

    EnvelopeDAHDSR envelope;

    public ModuleEG(Synthesizer synth) {
        envelope = new EnvelopeDAHDSR();
        synth.add(envelope);

        InputPort gate = new InputPort("gate", this, envelope.input);
        ports.add(gate);
        OutputPort out = new OutputPort("out", this, envelope.output);
        ports.add(out);

        envelope.hold.set(0.0);
        envelope.delay.set(0.0);

        envelope.attack.set(1.0);
        envelope.decay.set(1.0);
        envelope.sustain.set(0.0);
        envelope.release.set(1.0);

    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public void start() {
        envelope.start();
    }

    @Override
    public void stop() {
        envelope.stop();
    }

    @Override
    public void update() {

    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.EG;
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