package fr.synthlab.model.filter;

import com.jsyn.unitgen.UnitFilter;

public class FilterAttenuator extends UnitFilter {

    private double attenuation = 0; // Attenuation value in volts

    @Override
    public void generate(int start, int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();
        for (int i = start; i < limit; i++) {
            double in = inputs[i];
            double out = attenuation * in;
            outputs[i] = out;
        }
        System.out.println(outputs[0]);
    }

    public void setAttenuation(double attenuation) {
        this.attenuation = attenuation;
    }

    public double getAttenuation() {
        return this.attenuation;
    }
}
