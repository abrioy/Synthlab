package fr.synthlab.model.module.keyboard;

import com.jsyn.Synthesizer;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
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

    public ModuleKEYB(Synthesizer synth) {
    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

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
            case C : ; // C
            break;
            case CSharp : ; // C#
            break;
            case D : ; // D
            break;
            case DSharp : ; // D#
            break;
            case E : ; // E
            break;
            case F : ; // F
            break;
            case FSharp : ; // F#
            break;
            case G : ; // G
            break;
            case GSharp : ; // G#
            break;
            case A : ; // A
            break;
            case ASharp : ; // A#
            break;
            case B : ; // B
            break;
            case C2 : ; // C on octave sup
            break;
            case INCOCT : incrementOctave(); // increase octave
            break;
            case DECOCT : decrementOctave(); // decrease octave
            break;
        }
    }
}

