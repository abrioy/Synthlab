package fr.synthlab.model.module.sequencer;

import com.jsyn.unitgen.UnitFilter;

import java.util.List;

/**
 * Implementation of filter for the Sequencer.
 */
public class SEQFilterImpl extends UnitFilter implements SEQFilter {

    /**
     * Output voltage to the output of the sequencer.
     */
    private final List<Double> tension;

    /**
     * module sequence.
     */
    private ModuleSEQ seq;

    /**
     * current sequence.
     */
    private int current;

    /**
     * signal to change sequence.
     */
    private double signalFront;

    /**
     * is in attend of sigFrontStop.
     */
    private boolean attend;
    /**
     * signal to wait a signalFront.
     */
    private double sigFrontStop;

    /**
     * Constructor.
     *
     * @param stepValues list of value
     * @param seqInit        sequence
     */
    public SEQFilterImpl(final List<Double> stepValues,
                         final ModuleSEQ seqInit) {
        tension = stepValues;
        seq = seqInit;
        current = 0;

        signalFront = 0.1 / 2;
        sigFrontStop = -0.000001 / 2;
    }

    @Override
    public final void generate(final int start, final int limit) {
        double[] gates = output.getValues();
        double[] inputs = input.getValues();
        for (int i = start; i < limit; i += 1) {
            if (attend) {
                if (inputs[i] <= sigFrontStop) {
                    attend = false;
                }
            } else if (inputs[i] >= signalFront) {
                attend = true;
                current++;
                current = current % tension.size();
                seq.updateObs();
            }
            gates[i] = tension.get(current);
        }
    }

    @Override
    public final int getCurrent() {
        return current;
    }

    @Override
    public final void reset() {
        current = 0;
    }
}
