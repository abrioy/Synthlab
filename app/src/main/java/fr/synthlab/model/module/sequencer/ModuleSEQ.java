package fr.synthlab.model.module.sequencer;

import com.jsyn.Synthesizer;
import fr.synthlab.model.module.Module;
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

public class ModuleSEQ extends Observable implements Module {
    private static final Logger LOGGER = Logger.getLogger(ModuleSEQ.class.getName());

    /**
     * All ports
     */
    private ArrayList<Port> ports = new ArrayList<>();

    private SEQFilter seqFilter;
    private List<Double> stepValues;

    private Collection<Observer> observers;

    /**
     * Constructor
     *
     * @param synth Synthesizer
     */
    public ModuleSEQ(final Synthesizer synth) {
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

        seqFilter = new SEQFilter(stepValues, this);

        synth.add(seqFilter);
        InputPort gate = new InputPort("gate", this, seqFilter.input);
        OutputPort out = new OutputPort("out", this, seqFilter.output);
        ports.add(gate);
        ports.add(out);
    }

    /**
     * Getter on ports input and output.
     *
     * @return Scope port
     */
    @Override
    public final Collection<Port> getPorts() {
        return ports;
    }

    /**
     * Inherit method.
     */
    @Override
    public final void start() {
        seqFilter.start();
    }

    /**
     * Inherit method.
     */
    @Override
    public final void stop() {
        seqFilter.stop();
    }

    /**
     * Inherit method.
     */
    @Override
    public void update() {
    }

    @Override
    public final ModuleType getType() {
        return ModuleType.SEQ;
    }


    public final void setStepValue(final int step, final double value) {
        stepValues.set(step, value);
    }

    public final void addObserver(final Observer obs) {
        observers.add(obs);
    }

    public final void removeObserver(final Observer obs) {
        observers.remove(obs);
    }

    public final void updateObs() {
        for (Observer o : observers) {
            o.update(this, getCurrent());
        }
    }

    public final int getCurrent() {
        return seqFilter.getCurrent();
    }

    public final void reset() {
        seqFilter.reset();
    }
}
