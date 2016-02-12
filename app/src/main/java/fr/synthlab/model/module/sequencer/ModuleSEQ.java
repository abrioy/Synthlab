package fr.synthlab.model.module.sequencer;

import com.jsyn.Synthesizer;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.*;
import java.util.logging.Logger;

public class ModuleSEQ extends Observable implements Module {
    private static final Logger logger = Logger.getLogger(ModuleSEQ.class.getName());

    /**
     * All ports
     */
    private ArrayList<Port> ports = new ArrayList<>();

    private SEQFilter seqFilter;
    private List<Double> stepValues;

    private Collection<Observer> observers;

    /**
     * Constructor
     */
    public ModuleSEQ(Synthesizer synth) {

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
        seqFilter.start();
    }

    /**
     * Inherit method.
     */
    @Override
    public void stop() {
        seqFilter.stop();
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


    public void setStepValue(int step, double value) {
        stepValues.set(step, value);
    }

    public void addObserver(Observer obs){
        observers.add(obs);
    }

    public void removeObserver(Observer obs){
        observers.remove(obs);
    }

    public void updateObs(){
        for (Observer o : observers) {
            o.update(this, getCurrent());
        }
    }

    public int getCurrent(){
        return seqFilter.getCurrent();
    }

    public void reset() {
        seqFilter.reset();
    }
}
