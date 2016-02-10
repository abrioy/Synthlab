package fr.synthlab.model.module.keyboard;

import com.jsyn.Synthesizer;
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

    private int octave = 3;

    private List<Port> ports = new ArrayList<>();

    private OutputPort out;

    private OutputPort gate;

    private SineOscillator sineOscillator;

    private PassThrough passThrough;

    public ModuleKEYB(Synthesizer synth) {
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

    private void incrementOctave(){
        if(octave<7) {
            octave++;
        }
    }

    private void decrementOctave(){
        if(octave>0) {
            octave--;
        }
    }

    private void computeFrequency(Note note){
        double freq = REFERENCE_FREQUENCY * Math.pow(2, (note.getValue()/12.0))*Math.pow(2, (3 - octave));
        sineOscillator.frequency.setValueInternal(freq);
    }

    public void pressKey(Note n) {
        passThrough.getOutput().setValueInternal(5);
        switch(n) {
            case INCOCT :
                incrementOctave(); // increase octave
            break;
            case DECOCT :
                decrementOctave(); // decrease octave
            break;
            default:
                computeFrequency(n);
        }
    }

    public void releaseKey() {
        passThrough.getOutput().setValueInternal(-5);
    }
}

