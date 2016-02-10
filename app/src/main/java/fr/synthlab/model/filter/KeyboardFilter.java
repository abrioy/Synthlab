package fr.synthlab.model.filter;

import com.jsyn.unitgen.UnitFilter;

public class KeyboardFilter extends UnitFilter {

    /**
     * Output voltage to the gate output of the keyboard.
     */
    private int tension;

    /**
     * Constructor
     */
    public KeyboardFilter() {
        tension = 0;
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
        double[] outputs = output.getValues();
        for (int i = start; i < limit; i += 1) {
            outputs[i] = tension;
        }
    }
}
