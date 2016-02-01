package fr.synthlab.model.module;

import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;

public class VCOA implements Module {

    private Collection<Port> ports = new ArrayList<>();

    // Oscillators
    private SquareOscillator squareOscillator = new SquareOscillator();
    private TriangleOscillator triangleOscillator = new TriangleOscillator();
    private SawtoothOscillator sawtoothOscillator = new SawtoothOscillator();

    public VCOA() {
        ports.add(new InputPort("fm"));
        ports.add(new OutputPort("out"));
    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }
}
