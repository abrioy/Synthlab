package fr.synthlab.model.module.keyboard;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;
import fr.synthlab.model.filter.KeyboardFilter;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class ModuleKEYB implements Module {
    private static final Logger logger = Logger.getLogger(ModuleKEYB.class.getName());

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
    private final Note REFERENCE_NOTE = Note.A;

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
    private Note lastNotePressed;

    /**
     * Oscillator to generate frequency.
     */
    private SineOscillator sineOscillator;

    /**
     * Filter to manage the gate port.
     */
    private KeyboardFilter keyboardFilter;

    /**
     * Constructor
     * @param synth
     */
    public ModuleKEYB(Synthesizer synth) {
        octave = REFERENCE_OCTAVE;

        //Initialize
        sineOscillator = new SineOscillator();
        keyboardFilter = new KeyboardFilter();
        synth.add(sineOscillator);
        synth.add(keyboardFilter);

        //Output port
        OutputPort out = new OutputPort("out", this, sineOscillator.output);
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
        sineOscillator.start();
    }

    /**
     * Stop keyboard.
     */
    @Override
    public void stop() {
        sineOscillator.stop();
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
    public ModuleEnum getType() {
        return ModuleEnum.KEYB;
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
    public void pressKey(Note n) {
        keyboardFilter.pressKey();
        lastNotePressed = n;
        computeFrequency(n);
    }

    /**
     * Compute the new frequency.
     * @param n New note
     */
    private void computeFrequency(Note n){
        double freq = REFERENCE_FREQUENCY * Math.pow(2, (n.getValue()/12.0))*Math.pow(2, (octave - REFERENCE_OCTAVE));
        sineOscillator.frequency.setValueInternal(freq);
    }

    /**
     * Release the currently pressed key.
     */
    public void releaseKey() {
        keyboardFilter.releaseKey();
    }

    public int getOctave() {
        return octave;
    }
}
