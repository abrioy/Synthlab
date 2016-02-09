package fr.synthlab.model.filter;


import com.jsyn.unitgen.UnitFilter;

public class FmFilter extends UnitFilter {

    private double f0;

    public FmFilter(double f0) {
        this.f0 = f0;
    }

    @Override
    public void generate(int start, int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
            outputs[i] = f0 * Math.pow(2, inputs[i]);
        }
    }

    public void setf0(double v) {
        f0 = v;
    }
}
