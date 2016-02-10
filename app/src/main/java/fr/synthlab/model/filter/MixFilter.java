package fr.synthlab.model.filter;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import fr.synthlab.model.module.mixer.ModuleMixer;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;

public class MixFilter extends UnitGenerator{

    ModuleMixer mixer;

    public InputPort getInput1() {
        return input1;
    }

    public InputPort getInput4() {
        return input4;
    }

    public InputPort getInput3() {
        return input3;
    }

    public InputPort getInput2() {
        return input2;
    }

    private InputPort input1;
    private InputPort input2;
    private InputPort input3;
    private InputPort input4;
    private OutputPort output;

    public MixFilter(ModuleMixer mixer) {
        this.mixer = mixer;
        input1 = new InputPort("intern1",mixer,new UnitInputPort("intern1"));
        input2 = new InputPort("intern2",mixer,new UnitInputPort("intern2"));
        input3 = new InputPort("intern3",mixer,new UnitInputPort("intern3"));
        input4 = new InputPort("intern4",mixer,new UnitInputPort("intern4"));
        output = new OutputPort("internOutput", mixer, new UnitOutputPort("internOutput"));
    }

    @Override
    public void generate(int start, int limit) {
        double[] inputs1 = ((UnitInputPort) input1.getInput()).getValues();
        double[] inputs2 = ((UnitInputPort) input2.getInput()).getValues();
        double[] inputs3 = ((UnitInputPort) input3.getInput()).getValues();
        double[] inputs4 = ((UnitInputPort) input4.getInput()).getValues();
        double[] outputs = getOutput().getValues();

        for (int i = start; i < limit; i++) {
            outputs[i] = inputs1[i] + inputs2[i] + inputs3[i] + inputs4[i];
        }
    }

    public UnitOutputPort getOutput() {
        return ((UnitOutputPort) output.getOutput());
    }

}