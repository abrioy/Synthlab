package fr.synthlab.model.module.replicator;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 * Filter to replicator module.
 */
public interface FilterREP {
    /**
     * Generate new value for all output.
     *
     * @param start param manage by Jsyn
     * @param limit param manage by Jsyn
     */
    void generate(int start, int limit);

    /**
     * Getter on input.
     * @return in
     */
    UnitInputPort getIn();

    /**
     * Getter on Output 1.
     * @return out1
     */
    UnitOutputPort getOut1();

    /**
     * Getter on Output 2.
     * @return out2
     */
    UnitOutputPort getOut2();

    /**
     * Getter on Output 3.
     * @return out3
     */
    UnitOutputPort getOut3();
}
