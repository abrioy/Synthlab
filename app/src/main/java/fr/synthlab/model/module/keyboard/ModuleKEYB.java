package fr.synthlab.model.module.keyboard;

import com.jsyn.Synthesizer;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class ModuleKEYB implements Module {
    private static final Logger LOGGER = Logger.getLogger(ModuleKEYB.class.getName());

    /**
     * Reference frequency A3.
     */
    private final double referenceFrequency = 440.0;

    /**
     * Octave of the reference frequency.
     */
    private final int referenceOctave = 3;

    /**
     * Note corresponding to the reference frequency
     * (changing it will only change the initial note being played on the oscillo,
     * to generalize that a bit, we'd need to modify the formula in computeFrequency())
     */
    private final NoteKEYB referenceNote = NoteKEYB.A;

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
     * Constructor
     *
     * @param synth Synthesizer
     */
    public ModuleKEYB(final Synthesizer synth) {
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
        OutputPort gate = new OutputPort("gate", this, keyboardFilter.getGate());
        ports.add(gate);

        this.pressKey(referenceNote);
    }

    /**
     * Getter on ports output.
     *
     * @return Keyboard port
     */
    @Override
    public final Collection<Port> getPorts() {
        return ports;
    }

    /**
     * Start keyboard.
     */
    @Override
    public final void start() {
        filterOutKEYB.start();
    }

    /**
     * Stop keyboard.
     */
    @Override
    public final void stop() {
        filterOutKEYB.stop();
    }

    /**
     * Inherit method.
     */
    @Override
    public void update() {
    }

    /**
     * @return Type of this module
     */
    @Override
    public final ModuleType getType() {
        return ModuleType.KEYB;
    }

    /**
     * Change keyboard's octave
     *
     * @param newOctave New octave value
     */
    public final void changeOctave(int newOctave) {
        newOctave = Math.max(newOctave, octaveMin);
        newOctave = Math.min(newOctave, octaveMax);
        octave = newOctave;
        if (lastNotePressed != null) {
            computeFrequency(lastNotePressed);
        }
    }

    /**
     * Method to compute and send the new frequency in the oscilloscope.
     *
     * @param n New note pressed
     */
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
        filterOutKEYB.setTension(n.getValue() / 12.0 + (octave - referenceOctave));
    }

    /**
     * Release the currently pressed key.
     *
     * @param noteKEYB note release
     */
    public final void releaseKey(final NoteKEYB noteKEYB) {
        if (noteKEYB == lastNotePressed) {
            keyboardFilter.releaseKey();
        }
    }

    public final int getOctave() {
        return octave;
    }
}
