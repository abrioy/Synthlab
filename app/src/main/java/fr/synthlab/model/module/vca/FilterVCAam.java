package fr.synthlab.model.module.vca;


import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitFilter;
import com.softsynth.math.AudioMath;

public class FilterVCAam extends UnitFilter {
    private UnitOutputPort inputVca;

    public FilterVCAam(UnitOutputPort in) {
        inputVca = in;
    }

    @Override
    public void generate(int start, int limit) {

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
