package fr.synthlab.model.module.oscilloscope;

import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import org.apache.log4j.Logger;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;


public class ModuleOscilloscope implements Module {
    private static final Logger logger = Logger.getLogger(ModuleOscilloscope.class);

    private InputPort input;

    private AudioScope scope;

    public ModuleOscilloscope(Synthesizer synth) {
        this.input = new InputPort("in");
        scope = new AudioScope(synth);
        scope.start();
    }

    @Override
    public Collection<Port> getPorts() {
        Collection<Port> ports = new ArrayList<Port>();
        ports.add(input);
        return ports;
    }


}
