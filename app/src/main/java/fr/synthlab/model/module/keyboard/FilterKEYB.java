package fr.synthlab.model.module.keyboard;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

public class FilterKEYB extends UnitGenerator {

    /**
     * Output voltage to the gate output of the keyboard.
     */
    private double tension;

    private UnitOutputPort gate;

    /**
     * Constructor.
     */
    public FilterKEYB() {
        tension = 0;
        gate = new UnitOutputPort();
        this.addPort(gate);
    }

    public final void pressKey() {
        tension = 5;
    }

    public final void releaseKey() {
        tension = -5;
    }

    public final void generate(final int start, final int limit) {
        double[] gates = gate.getValues();
        for (int i = start; i < limit; i += 1) {
            gates[i] = tension;
        }
    }

    public final UnitOutputPort getGate() {
        return gate;
    }

    public final double getTension() {
        return tension;
    }
}
