package fr.synthlab.model.filter;

import com.jsyn.unitgen.UnitFilter;

public class KeyboardFilter extends UnitFilter {

    private int tension;

    public KeyboardFilter() {
        tension = 0;
    }

    public void pressKey(){
        tension = 5;
    }

    public void releaseKey(){
        tension = -5;
    }

    @Override
    public void generate(int start, int limit) {
        double[] outputs = output.getValues();
        for (int i = start; i < limit; i += 1) {
            outputs[i] = tension;
        }
    }
}
