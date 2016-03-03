package fr.synthlab.model.module.envelope;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.oscilloscope.ModuleSCOPImpl;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Implementation of module EG.
 * @see ModuleEG
 */
public class ModuleEGImpl implements ModuleEG {
    private static final Logger LOGGER
            = Logger.getLogger(ModuleSCOPImpl.class.getName());

    /**
     * All ports.
     */
    private ArrayList<Port> ports = new ArrayList<>();

    private EnvelopeDAHDSR envelope;

    public ModuleEGImpl(final Synthesizer synth) {
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

    @Override
    public final void setAttack(final double attack) {
        envelope.attack.set(attack);
    }

    @Override
    public final void setDecay(final double decay) {
        envelope.decay.set(decay);
    }

    @Override
    public final void setSustain(final double sustain) {
        envelope.sustain.set(sustain);
    }

    @Override
    public final void setRelease(final double release) {
        envelope.release.set(release);
    }

    @Override
    public final double getAttack() {
        return envelope.attack.get();
    }

    @Override
    public final double getDecay() {
        return envelope.decay.get();
    }

    @Override
    public final double getSustain() {
        return envelope.sustain.get();
    }

    @Override
    public final double getRelease() {
        return envelope.release.get();
    }
}
