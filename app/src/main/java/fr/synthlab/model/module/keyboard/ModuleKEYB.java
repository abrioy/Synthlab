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
import java.util.logging.Logger;

public class ModuleKEYB implements Module {
    private static final Logger logger = Logger.getLogger(ModuleKEYB.class.getName());

    private final int REFERENCE_FREQUENCY = 440;

    private int octave = 4;

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
        //keyboard.start();
    }

    @Override
    public void stop() {
        sineOscillator.stop();
        //keyboard.stop();
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

    public void pressKey(Note n) {
        switch(n) {
            case C : sineOscillator.output.setValueInternal(440); // C
            break;
            case CSharp : sineOscillator.output.setValueInternal(440); // C#
            break;
            case D : sineOscillator.output.setValueInternal(440); // D
            break;
            case DSharp : sineOscillator.output.setValueInternal(440); // D#
            break;
            case E : sineOscillator.output.setValueInternal(440); // E
            break;
            case F : sineOscillator.output.setValueInternal(440); // F
            break;
            case FSharp : sineOscillator.output.setValueInternal(440); // F#
            break;
            case G : sineOscillator.output.setValueInternal(440); // G
            break;
            case GSharp : sineOscillator.output.setValueInternal(440); // G#
            break;
            case A : sineOscillator.output.setValueInternal(440); // A
            break;
            case ASharp : sineOscillator.output.setValueInternal(440); // A#
            break;
            case B : sineOscillator.output.setValueInternal(440); // B
            break;
            case C2 : sineOscillator.output.setValueInternal(440); // C on octave sup
            break;
            case INCOCT : incrementOctave(); // increase octave
            break;
            case DECOCT : decrementOctave(); // decrease octave
            break;
        }
    }

    public void releaseKey() {

    }
}

