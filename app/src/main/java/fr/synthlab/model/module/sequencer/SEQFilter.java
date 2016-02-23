package fr.synthlab.model.module.sequencer;

import com.jsyn.unitgen.UnitFilter;

import java.util.List;

public class SEQFilter extends UnitFilter {

    /**
     * Output voltage to the output of the sequencer.
     */
    private final List<Double> tension;
    private ModuleSEQ seq;
    private int current;
    private double signalFront;
    private boolean attend;
    private double sigFrontStop;

    /**
     * Constructor
     * @param stepValues list of value
     * @param seq sequence
     */
    public SEQFilter(List<Double> stepValues, ModuleSEQ seq) {
        tension = stepValues;
        this.seq = seq;
        current = 0;

        signalFront = 0.1 / 2;
        sigFrontStop = -0.000001 / 2;
    }

    /**
     * Generate new values.Oscilloscope
     * @param start param managed by Jsyn
     * @param limit param managed by Jsyn
     */
    @Override
    public void generate(int start, int limit) {
        double[] gates = output.getValues();
        double[] inputs = input.getValues();
        for (int i = start; i < limit; i += 1) {
            if (attend) {
                if ( inputs[i] <= sigFrontStop ) {
                    attend = false;
                }
            } else if (inputs[i] >= signalFront ) {
                attend = true;
                current++;
                current= current % tension.size();
                seq.updateObs();
            }
            gates[i] = tension.get(current);
        }
    }

    public int getCurrent() {
        return current;
    }

    public void reset() {
        current=0;
    }
}
