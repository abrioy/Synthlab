package fr.synthlab.model.module.sequencer;

import com.jsyn.unitgen.UnitSink;
import com.jsyn.unitgen.UnitSource;

/**
 * filter for the Sequencer.
 */
public interface SEQFilter extends UnitSink, UnitSource {
    /**
     * Generate new values.Oscilloscope.
     *
     * @param start param managed by Jsyn
     * @param limit param managed by Jsyn
     */
    void generate(int start, int limit);

    /**
     * getter current sequence.
     * @return the current sequence
     */
    int getCurrent();

    /**
     * restart the sequence.
     */
    void reset();
}
