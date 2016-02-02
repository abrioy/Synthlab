package fr.synthlab.model.module.vcoa;

import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import fr.synthlab.model.filter.VcoFm;
import fr.synthlab.model.module.Module;
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

    private VcoFm fmFilter = new VcoFm(frequency);

    // Oscillators
    private SquareOscillator squareOscillator = new SquareOscillator();
    private TriangleOscillator triangleOscillator = new TriangleOscillator();
    private SawtoothOscillator sawtoothOscillator = new SawtoothOscillator();

    // Ports
    private InputPort fmInput;
    private OutputPort squareOutput;
    private OutputPort triangleOutput;
    private OutputPort sawtoothOutput;

    public VCOA() {
        fmInput = new InputPort("fm", this, fmFilter.input);
        squareOutput = new OutputPort("square", this, squareOscillator.output);
        triangleOutput = new OutputPort("triangle", this, triangleOscillator.output);
        sawtoothOutput = new OutputPort("sawtooth", this, sawtoothOscillator.output);

        ports.add(fmInput);
        ports.add(squareOutput);
        ports.add(triangleOutput);
        ports.add(sawtoothOutput);

        setFrequency(20);
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
        fmFilter.setf0(frequency * Math.pow(2, octave));

        if (fmInput.getConnected() == null) {
            squareOscillator.frequency.set(frequency * Math.pow(2, octave));
            triangleOscillator.frequency.set(frequency * Math.pow(2, octave));
            sawtoothOscillator.frequency.set(frequency * Math.pow(2, octave));
        }
    }

    public double getOctave() {
        return octave;
    }

    public void setOctave(double octave) {
        this.octave = octave;
        setFrequency(frequency);
    }

    public double getTone() {
        return tone;
    }

    public void setTone(double tone) {
        this.tone = tone;
    }

    @Override
    public void update() {
        if (fmInput.getConnected() == null) {
            fmFilter.output.disconnectAll();
            squareOscillator.frequency.set(frequency * Math.pow(2, octave));
            triangleOscillator.frequency.set(frequency * Math.pow(2, octave));
            sawtoothOscillator.frequency.set(frequency * Math.pow(2, octave));
        } else {
            fmFilter.output.connect(squareOscillator.frequency);
            fmFilter.output.connect(triangleOscillator.frequency);
            fmFilter.output.connect(sawtoothOscillator.frequency);
        }
    }
}
