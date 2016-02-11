package fr.synthlab.model.module.sequencer;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

public class SEQFilter extends UnitGenerator {

    /**
     * Output voltage to the output of the sequencer.
     */
    private double tension;

    private UnitOutputPort out;

    /**
     * Constructor
     */
    public SEQFilter() {
        tension = 0.0;
        out = new UnitOutputPort();
        this.addPort(out);
    }

    /**
     * Change the output tension of the generator
     */
    public void setTension(double tension){
        this.tension = tension;
    }

    /**
     * Generate new values.
     * @param start param managed by Jsyn
     * @param limit param managed by Jsyn
     */
    @Override
    public void generate(int start, int limit) {
        double[] gates = out.getValues();
        for (int i = start; i < limit; i += 1) {
            gates[i] = tension;
        }
    }

    public UnitOutputPort getOut() {
        return out;
    }
}
