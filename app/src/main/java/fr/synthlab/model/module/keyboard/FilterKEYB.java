package fr.synthlab.model.module.keyboard;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * Filter to keyboard.
 * @see UnitGenerator
 */
public class FilterKEYB extends UnitGenerator {

    /**
     * Output voltage to the gate output of the keyboard.
     */
    private double tension;

    /**
     * gate port of keyboard.
     */
    private UnitOutputPort gate;

    /**
     * Constructor.
     */
    public FilterKEYB() {
        tension = 0;
        gate = new UnitOutputPort();
        this.addPort(gate);
    }

    /**
     * action when press key.
     */
    public final void pressKey() {
        tension = 5;
    }

    /**
     * action when release key.
     */
    public final void releaseKey() {
        tension = -5;
    }

    @Override
    public final void generate(final int start, final int limit) {
        double[] gates = gate.getValues();
        for (int i = start; i < limit; i += 1) {
            gates[i] = tension;
        }
    }

    /**
     * getter on gate port.
     * @return port gate
     */
    public final UnitOutputPort getGate() {
        return gate;
    }

    /**
     * getter on current tension.
     * @return tension
     */
    public final double getTension() {
        return tension;
    }
}
