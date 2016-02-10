package fr.synthlab.model.module.keyboard;

import com.jsyn.Synthesizer;
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

    private OutputPort out;

    private OutputPort gate;

    private SineOscillator sineOscillator;

    private KeyboardFilter keyboardFilter;

    public ModuleKEYB(Synthesizer synth) {
        octave = REFERENCE_OCTAVE;
        sineOscillator = new SineOscillator();
        keyboardFilter = new KeyboardFilter();
        synth.add(sineOscillator);
        synth.add(keyboardFilter);
        out = new OutputPort("out", this, sineOscillator.output);
        ports.add(out);
        gate = new OutputPort("gate", this, keyboardFilter.output);
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
    }

    public void pressKey(Note n) {
        keyboardFilter.pressKey();
        double freq = REFERENCE_FREQUENCY * Math.pow(2, (n.getValue()/12.0))*Math.pow(2, (octave - REFERENCE_OCTAVE));
        sineOscillator.frequency.setValueInternal(freq);
    }

    public void releaseKey() {
        keyboardFilter.releaseKey();
    }
}

