package fr.synthlab.model.module.keyboard;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

public class FilterKEYB extends UnitGenerator {

    /**
     * Output voltage to the gate output of the keyboard.
     */
    private int tension;

    private UnitOutputPort gate;

    /**
     * Constructor
     */
    public FilterKEYB() {
        tension = 0;
        gate = new UnitOutputPort();
        this.addPort(gate);
    }

    /**
     * Press key, voltage change to 5V.
     */
    public void pressKey(){
        tension = 5;
    }

    /**
     * Release key, voltage change to -5V.
     */
    public void releaseKey(){
        tension = -5;
    }

    /**
     * Generate new values.
     * @param start param manage by Jsyn
     * @param limit param manage by Jsyn
     */
    @Override
    public void generate(int start, int limit) {
        double[] gates = gate.getValues();
        for (int i = start; i < limit; i += 1) {
            gates[i] = tension;
        }
    }

    public UnitOutputPort getGate() {
        return gate;
    }

    public int getTension() {
        return tension;
    }
}
