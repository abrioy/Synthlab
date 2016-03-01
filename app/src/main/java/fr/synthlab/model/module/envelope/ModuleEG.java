package fr.synthlab.model.module.envelope;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.oscilloscope.ModuleSCOP;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class ModuleEG implements Module {
    private static final Logger LOGGER
            = Logger.getLogger(ModuleSCOP.class.getName());

    /**
     * All ports.
     */
    private ArrayList<Port> ports = new ArrayList<>();

    private EnvelopeDAHDSR envelope;

    public ModuleEG(final Synthesizer synth) {
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
        envelope.sustain.set(0.5);
        envelope.release.set(1.0);
    }

    @Override
    public final Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public final void start() {
        envelope.start();
    }

    @Override
    public final void stop() {
        envelope.stop();
    }

    @Override
    public void update() {
    }

    @Override
    public final ModuleType getType() {
        return ModuleType.EG;
    }

    public final void setAttack(final double attack) {
        envelope.attack.set(attack);
    }

    public final void setDecay(final double decay) {
        envelope.decay.set(decay);
    }

    public final void setSustain(final double sustain) {
        envelope.sustain.set(sustain);
    }

    public final void setRelease(final double release) {
        envelope.release.set(release);
    }

    public final double getAttack() {
        return envelope.attack.get();
    }

    public final double getDecay() {
        return envelope.decay.get();
    }

    public final double getSustain() {
        return envelope.sustain.get();
    }

    public final double getRelease() {
        return envelope.release.get();
    }
}
