package fr.synthlab.model.module.replicator;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

public class FilterREPImpl extends UnitGenerator implements FilterREP {

    /**
     * Input.
     */
    private UnitInputPort in;

    /**
     * Output 1.
     */
    private UnitOutputPort out1;

    /**
     * Output 2.
     */
    private UnitOutputPort out2;

    /**
     * Output 3.
     */
    private UnitOutputPort out3;

    /**
     * Constructor.
     */
    public FilterREPImpl() {
        in = new UnitInputPort("in");
        out1 = new UnitOutputPort("out1");
        out2 = new UnitOutputPort("out2");
        out3 = new UnitOutputPort("out3");
        addPort(in);
        addPort(out1);
        addPort(out2);
        addPort(out3);
    }

    @Override
    public final void generate(final int start, final int limit) {
        double[] inputs = in.getValues();
        double[] outputs1 = out1.getValues();
        double[] outputs2 = out2.getValues();
        double[] outputs3 = out3.getValues();
        for (int i = start; i < limit; i++) {
            outputs1[i] = inputs[i];
            outputs2[i] = inputs[i];
            outputs3[i] = inputs[i];
        }
    }

    @Override
	public final UnitInputPort getIn() {
        return in;
    }

    @Override
	public final UnitOutputPort getOut1() {
        return out1;
    }

    @Override
	public final UnitOutputPort getOut2() {
        return out2;
    }

    @Override
	public final UnitOutputPort getOut3() {
        return out3;
    }
}
