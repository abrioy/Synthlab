package fr.synthlab.model.module.sequencer;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitGatePort;
import com.jsyn.unitgen.PassThrough;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class ModuleSEQ implements Module {
    private static final Logger logger = Logger.getLogger(ModuleSEQ.class.getName());

    /**
     * All ports
     */
    private ArrayList<Port> ports = new ArrayList<>();

    private int step;
    private SEQFilter seqFilter;
    private List<Double> stepValues;

    /**
     * Constructor
     */
    public ModuleSEQ(Synthesizer synth) {
        step = 0;

        seqFilter = new SEQFilter();
        stepValues = new ArrayList<Double>();

        stepValues.add(0.0);
        stepValues.add(0.0);
        stepValues.add(0.0);
        stepValues.add(0.0);

        stepValues.add(0.0);
        stepValues.add(0.0);
        stepValues.add(0.0);
        stepValues.add(0.0);

        InputPort gate = new InputPort("gate", this, new UnitGatePort("gate"));
        OutputPort out = new OutputPort("out", this, seqFilter.getOut());
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

    }

    /**
     * Inherit method.
     */
    @Override
    public void stop() {

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

    private void nextStep() {
        step = (step + 1) % 8;
        seqFilter.setTension(stepValues.get(step));
    }

    public void setStepValue(int step, double value) {
        stepValues.set(step, value);
    }

    public void resetStep() {
        step = 0;
    }
}
