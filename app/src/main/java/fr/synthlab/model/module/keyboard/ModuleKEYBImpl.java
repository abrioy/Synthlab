package fr.synthlab.model.module.keyboard;

import com.jsyn.Synthesizer;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation module Keyboard.
 * @see ModuleKEYB
 */
public class ModuleKEYBImpl implements ModuleKEYB {
    private static final Logger LOGGER
            = Logger.getLogger(ModuleKEYBImpl.class.getName());

    /**
     * Reference frequency A3.
     */
    private final double referenceFrequency = 440.0;

    /**
     * Octave of the reference frequency.
     */
    private final int referenceOctave = 3;

    /**
     * Note corresponding to the reference frequency.
     * (changing it will only change the initial note
     * being played on the oscillo,
     * to generalize that a bit, we'd need to modify
     * the formula in computeFrequency()).
     */
    private final NoteKEYB referenceNote = NoteKEYBImpl.A;

    /**
     * Minimum octave.
     */
    private final int octaveMin = 0;

    /**
     * Maximum octave.
     */
    private final int octaveMax = 7;

    /**
     * Current octave.
     */
    private int octave;

    /**
     * List of ports.
     * Contains out and gate.
     */
    private List<Port> ports = new ArrayList<>();

    /**
     * Save the last note pressed. Useful during octave changing.
     */
    private NoteKEYB lastNotePressed;

    /**
     * Oscillator to generate frequency.
     */
    private FilterOutKEYB filterOutKEYB;

    /**
     * Filter to manage the gate port.
     */
    private FilterKEYB keyboardFilter;

    /**
     * Constructor.
     *
     * @param synth Synthesizer
     */
    public ModuleKEYBImpl(final Synthesizer synth) {
        octave = referenceOctave;

        //Initialize
        filterOutKEYB = new FilterOutKEYB();
        keyboardFilter = new FilterKEYB();
        synth.add(filterOutKEYB);
        synth.add(keyboardFilter);

        //Output port
        OutputPort out = new OutputPort("out", this, filterOutKEYB.getGate());
        ports.add(out);

        //Gate port
        OutputPort gate
                = new OutputPort("gate", this, keyboardFilter.getGate());
        ports.add(gate);

        this.pressKey(referenceNote);
    }

    @Override
    public final Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public final void start() {
        filterOutKEYB.start();
    }

    @Override
    public final void stop() {
        filterOutKEYB.stop();
    }

    @Override
    public void update() {
    }

    @Override
    public final ModuleType getType() {
        return ModuleType.KEYB;
    }

    @Override
    public final void changeOctave(int newOctave) {
        newOctave = Math.max(newOctave, octaveMin);
        newOctave = Math.min(newOctave, octaveMax);
        octave = newOctave;
        if (lastNotePressed != null) {
            computeFrequency(lastNotePressed);
        }
    }

    @Override
    public final void pressKey(final NoteKEYB n) {
        keyboardFilter.releaseKey();
        keyboardFilter.pressKey();
        lastNotePressed = n;
        computeFrequency(n);
    }

    /**
     * Compute the new frequency.
     *
     * @param n New note
     */
    private void computeFrequency(final NoteKEYB n) {
        filterOutKEYB.setTension(n.getValue()
                / 12.0 + (octave - referenceOctave));
    }

    @Override
    public final void releaseKey(final NoteKEYB noteKEYB) {
        if (noteKEYB == lastNotePressed) {
            keyboardFilter.releaseKey();
        }
    }

    @Override
    public final int getOctave() {
        return octave;
    }
}
