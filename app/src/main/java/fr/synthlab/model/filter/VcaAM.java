package fr.synthlab.model.filter;


import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitFilter;
import com.softsynth.math.AudioMath;

public class VcaAM extends UnitFilter {
    public UnitOutputPort inputVca;

    public VcaAM(UnitOutputPort in) {
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
            outputs[i] = inVca * AudioMath.decibelsToAmplitude(am * 12);
        }
    }
}
