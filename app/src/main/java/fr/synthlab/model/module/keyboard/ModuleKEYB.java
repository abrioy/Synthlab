package fr.synthlab.model.module.keyboard;

import com.jsyn.Synthesizer;
import com.jsyn.data.Function;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.SineOscillator;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class ModuleKEYB implements Module {
    private static final Logger logger = Logger.getLogger(ModuleKEYB.class.getName());

    private final double REFERENCE_FREQUENCY = 440.0;
    private final int REFERENCE_OCTAVE = 3;
    private final int OCTAVE_MIN = 0;
    private final int OCTAVE_MAX = 7;

    private int octave;

    private List<Port> ports = new ArrayList<>();

    private Note lastNotePressed;

    private OutputPort out;
    private OutputPort gate;

    private SineOscillator sineOscillator;
    private PassThrough passThrough;

    public ModuleKEYB(Synthesizer synth) {
        octave = REFERENCE_OCTAVE;
        sineOscillator = new SineOscillator();
        passThrough = new PassThrough();
        synth.add(sineOscillator);
        synth.add(passThrough);
        out = new OutputPort("out", this, sineOscillator.output);
        ports.add(out);
        gate = new OutputPort("gate", this, passThrough.output);
        ports.add(gate);
    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public void start() {
        sineOscillator.start();
    }

    @Override
    public void stop() {
        sineOscillator.stop();
    }

    @Override
    public void update() {

    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.KEYB;
    }

    public void changeOctave(int newOctave) {
        newOctave = Math.max(newOctave, OCTAVE_MIN);
        newOctave = Math.min(newOctave, OCTAVE_MAX);
        this.octave = newOctave;
        if (lastNotePressed != null) {
            pressKey(lastNotePressed);
        }
    }

    public void pressKey(Note n) {
        lastNotePressed = n;
        passThrough.getOutput().setValueInternal(5);
        double freq = REFERENCE_FREQUENCY * Math.pow(2, (n.getValue()/12.0))*Math.pow(2, (octave - REFERENCE_OCTAVE));
        sineOscillator.frequency.setValueInternal(freq);
    }

    public void releaseKey() {
        passThrough.getOutput().setValueInternal(-5);
    }

}
