package fr.synthlab.model.module.keyboard;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.SineOscillator;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModuleKEYB implements Module {
    private static final Logger logger = Logger.getLogger(ModuleKEYB.class.getName());

    private final double REFERENCE_FREQUENCY = 440.0;

    private int octave = 3;

    private int gateValue = 0;

    private int lastKeyPressed;

    private List<Port> ports = new ArrayList<>();

    private OutputPort out;

    private OutputPort gate;

    private SineOscillator sineOscillator;

    public ModuleKEYB(Synthesizer synth) {
        sineOscillator = new SineOscillator();
        synth.add(sineOscillator);
        out = new OutputPort("out", this, sineOscillator.output);
        ports.add(out);
        UnitOutputPort g = new UnitOutputPort();
        gate = new OutputPort("gate", this, g);
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
        //TODO limit?
        octave++;
    }

    private void decrementOctave(){
        if(octave>0) {
            octave--;
        }
    }

    private void computeFrequency(Note note){
        logger.log(Level.INFO, "ref : " + REFERENCE_FREQUENCY + " note :" + note.getValue() + " octave :" + (3 - octave));
        double freq = REFERENCE_FREQUENCY * Math.pow(2, (note.getValue()/12.0))*Math.pow(2, (3 - octave));
        logger.log(Level.INFO, "frequency : " + freq);
        sineOscillator.frequency.setValueInternal(freq);
    }

    public void pressKey(Note n) {
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

    }
}

