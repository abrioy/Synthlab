package fr.synthlab.model.module.mixer;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

public class FilterMIX extends UnitGenerator{

    /**
     * input 1.
     */
    private UnitInputPort input1;

    /**
     * input 2.
     */
    private UnitInputPort input2;

    /**
     * input 3.
     */
    private UnitInputPort input3;

    /**
     * input 4.
     */
    private UnitInputPort input4;

    /**
     * output.
     */
    private UnitOutputPort output;

    /**
     * constructor.
     */
    public FilterMIX() {

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

    /**
     * getter on input1.
     *
     * @return input 1
     */
    public UnitInputPort getInput1() {
        return input1;
    }

    /**
     * getter on input2.
     *
     * @return input 2
     */
    public UnitInputPort getInput2() {
        return input2;
    }

    /**
     * getter on input3.
     *
     * @return input 3
     */
    public UnitInputPort getInput3() {
        return input3;
    }

    /**
     * getter on input4.
     *
     * @return input 4
     */
    public UnitInputPort getInput4() {
        return input4;
    }

    /**
     * generate new value for output.
     *
     * @param start debut
     * @param limit fin
     */
    @Override
    public void generate(int start, int limit) {
        double[] inputs1 = input1.getValues();
        double[] inputs2 = input2.getValues();
        double[] inputs3 = input3.getValues();
        double[] inputs4 = input4.getValues();
        double[] outputs = output.getValues();
        int div;
        for (int i = start; i < limit; i++) {
            div = 0;
            if (input1.isConnected()) {
                div++;
            }
            if (input2.isConnected()) {
                div++;
            }
            if (input3.isConnected()) {
                div++;
            }
            if (input4.isConnected()) {
                div++;
            }
            outputs[i] = (inputs1[i] + inputs2[i] + inputs3[i] + inputs4[i]) / div;
        }
    }

    /**
     * getter on output.
     * @return output
     */
    public UnitOutputPort getOutput() {
        return output;
    }

}