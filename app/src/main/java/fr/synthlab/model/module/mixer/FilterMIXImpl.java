package fr.synthlab.model.module.mixer;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * Implement in mix filter.
 * @see UnitGenerator
 * @see FilterMIX
 */
public class FilterMIXImpl extends UnitGenerator implements FilterMIX {

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
    public FilterMIXImpl() {
        input1 = new UnitInputPort("intern1");
        input2 = new UnitInputPort("intern2");
        input3 = new UnitInputPort("intern3");
        input4 = new UnitInputPort("intern4");
        output = new UnitOutputPort("out");
        addPort(input1);
        addPort(input2);
        addPort(input3);
        addPort(input4);
        addPort(output);
    }

    @Override
    public final UnitInputPort getInput1() {
        return input1;
    }

    @Override
    public final UnitInputPort getInput2() {
        return input2;
    }

    @Override
    public final UnitInputPort getInput3() {
        return input3;
    }

    @Override
    public final UnitInputPort getInput4() {
        return input4;
    }

    @Override
    public final void generate(final int start, final int limit) {
        double[] inputs1 = input1.getValues();
        double[] inputs2 = input2.getValues();
        double[] inputs3 = input3.getValues();
        double[] inputs4 = input4.getValues();
        double[] outputs = output.getValues();
        int div;
        double sum;
        for (int i = start; i < limit; i++) {
            sum = 0;
            div = 0;
            if (input1.isConnected()) {
                sum += inputs1[i];
                div++;
            }
            if (input2.isConnected()) {
                sum += inputs2[i];
                div++;
            }
            if (input3.isConnected()) {
                sum += inputs3[i];
                div++;
            }
            if (input4.isConnected()) {
                sum += inputs4[i];
                div++;
            }
            outputs[i] = sum / div;
        }
    }

    @Override
    public final UnitOutputPort getOutput() {
        return output;
    }
}
