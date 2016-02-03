package fr.synthlab.model.filter;

import com.jsyn.unitgen.UnitFilter;
import java.util.logging.Logger;

/**
 * attenuator filter
 * @author johan
 */
public class FilterAttenuator extends UnitFilter {
    /**
     * logger
     */
    private static final Logger logger = Logger.getLogger(FilterAttenuator.class.getName());

    /**
     * attenuation
     */
    private double attenuation = 1;

    /**
     * generate new values
     * @param start param manage by Jsyn
     * @param limit param manage by Jsyn
     */
    @Override
    public void generate(int start, int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();
        for (int i = start; i < limit; i++) {
            double in = inputs[i];
            double out = attenuation * in;
            outputs[i] = out;
        }
    }

    /**
     * setter attenuation
     * @param attenuation attenuation param
     */
    public void setAttenuation(double attenuation) {
        if (attenuation > 12){
            attenuation = 12;
        }
        this.attenuation = attenuation;
    }

    /**
     * getter on attenuation
     * @return attenuation
     */
    public double getAttenuation() {
        return this.attenuation;
    }
}
