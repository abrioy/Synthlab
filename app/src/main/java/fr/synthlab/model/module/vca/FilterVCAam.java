package fr.synthlab.model.module.vca;


import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitFilter;
import com.softsynth.math.AudioMath;

/**
 * filter for the VCA.
 */
public class FilterVCAam extends UnitFilter {
    /**
     * input of filter.
     */
    private UnitOutputPort inputVca;

    /**
     * constructor.
     * @param in input
     */
    public FilterVCAam(final UnitOutputPort in) {
        inputVca = in;
    }

    @Override
    public final void generate(final int start, final int limit) {

        double[] inputs = input.getValues();
        double[] inputsVca = inputVca.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
            double am = inputs[i];
            double inVca = inputsVca[i];

            if (am > 0.0 || am < 0.0) {
                outputs[i] = inVca * AudioMath.decibelsToAmplitude(am * 12);
            } else {
                outputs[i] = 0.0;
            }
        }
    }
}
