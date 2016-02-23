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
    private final double REFERENCE_FREQUENCY = 440.0;

    /**
     * Octave of the reference frequency.
     */
    private final int REFERENCE_OCTAVE = 3;

    /**
     * Note corresponding to the reference frequency
     * (changing it will only change the initial note being played on the oscillo,
     * to generalize that a bit, we'd need to modify the formula in computeFrequency())
     */
    private final NoteKEYB REFERENCE_NOTE = NoteKEYB.A;

    /**
     * Minimum octave.
     */
    private final int OCTAVE_MIN = 0;

    /**
     * Maximum octave.
     */
    private final int OCTAVE_MAX = 7;

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
     * @param synth Synthesizer
     */
    public ModuleKEYB(Synthesizer synth) {
        octave = REFERENCE_OCTAVE;

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

        this.pressKey(REFERENCE_NOTE);
    }

    /**
     * Getter on ports output.
     * @return Keyboard port
     */
    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    /**
     * Start keyboard.
     */
    @Override
    public void start() {
        filterOutKEYB.start();
    }

    /**
     * Stop keyboard.
     */
    @Override
    public void stop() {
        filterOutKEYB.stop();
    }

    /**
     * Inherit method.
     */
    @Override
    public void update() {

    }

    /**
     *
     * @return Type of this module
     */
    @Override
    public ModuleType getType() {
        return ModuleType.KEYB;
    }

    /**
     * Change keyboard's octave
     * @param newOctave New octave value
     */
    public void changeOctave(int newOctave) {
        newOctave = Math.max(newOctave, OCTAVE_MIN);
        newOctave = Math.min(newOctave, OCTAVE_MAX);
        this.octave = newOctave;
        if (lastNotePressed != null) {
            computeFrequency(lastNotePressed);
        }
    }

    /**
     * Method to compute and send the new frequency in the oscilloscope.
     * @param n New note pressed
     */
    public void pressKey(NoteKEYB n) {
        keyboardFilter.pressKey();
        lastNotePressed = n;
        computeFrequency(n);
    }

    /**
     * Compute the new frequency.
     * @param n New note
     */
    private void computeFrequency(NoteKEYB n){
        filterOutKEYB.setTension(n.getValue()/12.0 + (octave - REFERENCE_OCTAVE));
    }

    /**
     * Release the currently pressed key.
     */
    public void releaseKey(NoteKEYB noteKEYB) {
        if (noteKEYB==lastNotePressed) {
            keyboardFilter.releaseKey();
        }
    }

    public int getOctave() {
        return octave;
    }
}
