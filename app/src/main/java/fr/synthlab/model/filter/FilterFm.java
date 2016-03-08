package fr.synthlab.model.filter;

import com.jsyn.unitgen.UnitFilter;

/**
 * Filter for VCOA and VCF.
 * @see UnitFilter
 */
public class FilterFm extends UnitFilter {

    /**
     * Modulation frequency.
     */
    private double f0;

    /**
     * constructor.
     * @param f0Init to set on f0
     */
    public FilterFm(final double f0Init) {
        f0 = f0Init;
    }

    /**
     * generate tension.
     * @param start start
     * @param limit stop
     */
    @Override
    public final void generate(final int start, final int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
            outputs[i] = f0 * Math.pow(2, inputs[i]);
        }
    }

    /**
     * setter on f0.
     * @param v to set on f0
     */
    public final void setF0(final double v) {
        f0 = v;
    }
}
