package fr.synthlab.model.module;

import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import fr.synthlab.model.module.filter.VcoFm;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class VCOA implements Module {
	private static final Logger logger = Logger.getLogger(VCOA.class);

    private Collection<Port> ports = new ArrayList<>();

    private double frequency = 20;
    private double octave = 0;
    private double tone = 0;

    private VcoFm fm = new VcoFm(frequency);

    // Oscillators
    private SquareOscillator squareOscillator = new SquareOscillator();
    private TriangleOscillator triangleOscillator = new TriangleOscillator();
    private SawtoothOscillator sawtoothOscillator = new SawtoothOscillator();

    public VCOA() {
        ports.add(new InputPort("fm", this, fm.input));
        ports.add(new OutputPort("square", this, squareOscillator.output));
        ports.add(new OutputPort("triangle", this, triangleOscillator.output));
        ports.add(new OutputPort("sawtooth", this, sawtoothOscillator.output));
    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
        fm.setf0(frequency);
    }

    public double getOctave() {
        return octave;
    }

    public void setOctave(double octave) {
        this.octave = octave;
    }

    public double getTone() {
        return tone;
    }

    public void setTone(double tone) {
        this.tone = tone;
    }
}
