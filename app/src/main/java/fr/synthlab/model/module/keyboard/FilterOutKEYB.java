package fr.synthlab.model.module.keyboard;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

public class FilterOutKEYB extends UnitGenerator {

    /**
     * Output voltage to the gate output of the keyboard.
     */
    private double tension;

    private UnitOutputPort gate;

    /**
     * Constructor.
     */
    public FilterOutKEYB() {
        tension = 0;
        gate = new UnitOutputPort();
        this.addPort(gate);
    }

    /**
     * Generate new values.
     * @param start param manage by Jsyn
     * @param limit param manage by Jsyn
     */
    @Override
    public final void generate(final int start, final int limit) {
        double[] gates = gate.getValues();
        for (int i = start; i < limit; i += 1) {
            gates[i] = tension;
        }
    }

    public final void setTension(final double newTension) {
        tension = newTension;
    }

    public final UnitOutputPort getGate() {
        return gate;
    }
}
