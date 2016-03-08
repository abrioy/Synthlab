package fr.synthlab.model.module.keyboard;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * Filter out to keyboard.
 * @see UnitGenerator
 */
public class FilterOutKEYB extends UnitGenerator {

    /**
     * Output voltage to the gate output of the keyboard.
     */
    private double tension;

    /**
     * port gate.
     */
    private UnitOutputPort gate;

    /**
     * Constructor.
     */
    public FilterOutKEYB() {
        tension = 0;
        gate = new UnitOutputPort();
        this.addPort(gate);
    }

    @Override
    public final void generate(final int start, final int limit) {
        double[] gates = gate.getValues();
        for (int i = start; i < limit; i += 1) {
            gates[i] = tension;
        }
    }

    /**
     * setter on tension.
     * @param newTension tension to set
     */
    public final void setTension(final double newTension) {
        tension = newTension;
    }

    /**
     * getter on gate.
     * @return port gate
     */
    public final UnitOutputPort getGate() {
        return gate;
    }
}
