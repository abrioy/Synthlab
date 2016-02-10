package fr.synthlab.model.filter;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import fr.synthlab.model.module.mixer.ModuleMixer;

public class MixFilter extends UnitGenerator{

    @Override
    public void start(){
        super.start();
      //  passThrough.start();
    }

    //private final PassThrough passThrough;
    //ModuleMixer mixer;

    public UnitInputPort getInput1() {
        return input1;
    }

    public UnitInputPort getInput4() {
        return input4;
    }

    public UnitInputPort getInput3() {
        return input3;
    }

    public UnitInputPort getInput2() {
        return input2;
    }

    private UnitInputPort input1;
    private UnitInputPort input2;
    private UnitInputPort input3;
    private UnitInputPort input4;
    private UnitOutputPort output;

    public MixFilter(ModuleMixer mixer) {
        //passThrough = new PassThrough();
        //this.mixer = mixer;

        //mixer.getSyn().add(passThrough);

        input1 = new UnitInputPort("intern1");
        input2 = new UnitInputPort("intern2");
        input3 = new UnitInputPort("intern3");
        input4 = new UnitInputPort("intern4");
        output =  new UnitOutputPort("out");
        addPort(input1);
        addPort(input2);
        addPort(input3);
        addPort(input4);
        addPort(output);
    }

    @Override
    public void generate(int start, int limit) {
        double[] inputs1 = input1.getValues();
        double[] inputs2 = input2.getValues();
        double[] inputs3 = input3.getValues();
        double[] inputs4 = input4.getValues();
        double[] outputs = output.getValues();
        for (int i = start; i < limit; i++) {
            outputs[i] = inputs1[i] + inputs2[i] + inputs3[i] + inputs4[i];
        }
    }

    public UnitOutputPort getOutput() {
        return output;
    }

}