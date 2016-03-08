package fr.synthlab.model.module.sequencer;

import com.jsyn.Synthesizer;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of module sequence.
 */
public class ModuleSEQImpl extends Observable implements ModuleSEQ {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(ModuleSEQImpl.class.getName());

    /**
     * All ports.
     */
    private ArrayList<Port> ports = new ArrayList<>();

    /**
     * filter for detect change of sequence.
     */
    private SEQFilterImpl seqFilter;
    /**
     * sequence.
     */
    private List<Double> stepValues;

    /**
     * observer of current sequence.
     */
    private Collection<Observer> observers;

    /**
     * Constructor.
     *
     * @param synth Synthesizer
     */
    public ModuleSEQImpl(final Synthesizer synth) {
        observers = new ArrayList<>();

        stepValues = new ArrayList<>();
        stepValues.add(0.0);
        stepValues.add(0.0);
        stepValues.add(0.0);
        stepValues.add(0.0);

        stepValues.add(0.0);
        stepValues.add(0.0);
        stepValues.add(0.0);
        stepValues.add(0.0);

        seqFilter = new SEQFilterImpl(stepValues, this);

        synth.add(seqFilter);
        InputPort gate = new InputPort("gate", this, seqFilter.input);
        OutputPort out = new OutputPort("out", this, seqFilter.output);
        ports.add(gate);
        ports.add(out);
    }

    @Override
    public final Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public final void start() {
        seqFilter.start();
    }

    @Override
    public final void stop() {
        seqFilter.stop();
    }

    @Override
    public void update() {
    }

    @Override
    public final ModuleType getType() {
        return ModuleType.SEQ;
    }


    @Override
    public final void setStepValue(final int step, final double value) {
        stepValues.set(step, value);
    }

    @Override
    public final void addObserver(final Observer obs) {
        observers.add(obs);
    }

    @Override
    public final void removeObserver(final Observer obs) {
        observers.remove(obs);
    }

    @Override
    public final void updateObs() {
        for (Observer o : observers) {
            o.update(this, getCurrent());
        }
    }

    @Override
    public final int getCurrent() {
        return seqFilter.getCurrent();
    }

    @Override
    public final void reset() {
        seqFilter.reset();
    }
}
