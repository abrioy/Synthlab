package fr.synthlab.model.filter;


import com.jsyn.unitgen.UnitFilter;

public class FilterFm extends UnitFilter {

    private double f0;

    public FilterFm(final double f0Init) {
        f0 = f0Init;
    }

    @Override
    public final void generate(final int start, final int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
            outputs[i] = f0 * Math.pow(2, inputs[i]);
        }
    }

    public final void setF0(final double v) {
        f0 = v;
    }
}
