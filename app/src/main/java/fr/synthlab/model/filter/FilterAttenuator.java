package fr.synthlab.model.filter;

import com.jsyn.unitgen.UnitFilter;
import com.softsynth.math.AudioMath;

import java.util.logging.Logger;

/**
 * attenuator filter.
 * @author johan
 */
public class FilterAttenuator extends UnitFilter {
    /**
     * LOGGER.
     */
    private static final Logger LOGGER
            = Logger.getLogger(FilterAttenuator.class.getName());

    /**
     * attenuation amplitude.
     */
    private double attenuation = 1;

    /**
     * generate new values.
     * @param start param manage by Jsyn
     * @param limit param manage by Jsyn
     */
    @Override
    public final void generate(final int start, final int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();
        for (int i = start; i < limit; i++) {
            double in = inputs[i];
            double out = in * attenuation;
            outputs[i] = out;
        }
    }

    /**
     * getter on attenuation.
     * @return attenuation
     */
    public final double getAttenuation() {
        return this.attenuation;
    }

    /**
     * setter attenuation in dB.
     * @param decibels attenuation param
     */
    public final void setAttenuation(final double decibels) {
        this.attenuation = AudioMath.decibelsToAmplitude(decibels);
    }
}
