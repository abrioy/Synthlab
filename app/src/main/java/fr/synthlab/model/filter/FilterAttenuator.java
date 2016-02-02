package fr.synthlab.model.filter;

import com.jsyn.unitgen.UnitFilter;
import java.util.logging.Logger;

public class FilterAttenuator extends UnitFilter {
    private static final Logger logger = Logger.getLogger(FilterAttenuator.class.getName());

    private double attenuation = 1; // Attenuation value in volts

    @Override
    public void generate(int start, int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();
        for (int i = start; i < limit; i++) {
            double in = inputs[i];
            double out = attenuation * in;/*Math.abs(attenuation) * Math.abs(in);
            if (attenuation < 0 || in < 0 ){
                out = -out;
            }*/
            outputs[i] = out;
        }
    }

    public void setAttenuation(double attenuation) {
        this.attenuation = attenuation;
    }

    public double getAttenuation() {
        return this.attenuation;
    }
}
