package fr.synthlab.model.module.filter;


import com.jsyn.unitgen.UnitFilter;

public class VcoFm extends UnitFilter {

    private double f0;

    public VcoFm(double f0) {
        this.f0 = f0;
    }

    @Override
    public void generate(int start, int limit) {
        for (int i = start; i < limit; i++) {
            output.getValues()[i] = f0 * Math.pow(2, input.getValue(i));
        }
    }

    public void setf0(double v) {
        f0 = v;
    }
}
