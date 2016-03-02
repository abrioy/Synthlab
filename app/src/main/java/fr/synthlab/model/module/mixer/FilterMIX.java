package fr.synthlab.model.module.mixer;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 * Filter to mix module.
 */
public interface FilterMIX {
    /**
     * getter on input1.
     *
     * @return input 1
     */
    UnitInputPort getInput1();

    /**
     * getter on input2.
     *
     * @return input 2
     */
    UnitInputPort getInput2();

    /**
     * getter on input3.
     *
     * @return input 3
     */
    UnitInputPort getInput3();

    /**
     * getter on input4.
     *
     * @return input 4
     */
    UnitInputPort getInput4();

    /**
     * generate new value for output.
     *
     * @param start debut
     * @param limit fin
     */
    void generate(int start, int limit);

    /**
     * getter on output.
     *
     * @return output
     */
    UnitOutputPort getOutput();
}
