package fr.synthlab.model.filter;


import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.UnitFilter;

public class VcaAM extends UnitFilter {
    public UnitInputPort inputVca;

    public VcaAM(UnitInputPort in) {
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
            outputs[i] = inVca * (am * 12);
        }
    }
}
