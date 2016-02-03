package fr.synthlab.model.module.vcoa;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import fr.synthlab.model.filter.VcoFm;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class ModuleVCOA implements Module {
	private static final Logger logger = Logger.getLogger(ModuleVCOA.class.getName());

    private Collection<Port> ports = new ArrayList<>();

    private double frequency = 450;
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

    public ModuleVCOA(Synthesizer synthesizer) {
        synthesizer.add(squareOscillator);
        synthesizer.add(triangleOscillator);
        synthesizer.add(sawtoothOscillator);
        synthesizer.add(fmFilter);

        fmInput = new InputPort("fm", this, fmFilter.input);
        squareOutput = new OutputPort("square", this, squareOscillator.output);
        triangleOutput = new OutputPort("triangle", this, triangleOscillator.output);
        sawtoothOutput = new OutputPort("sawtooth", this, sawtoothOscillator.output);

        ports.add(fmInput);
        ports.add(squareOutput);
        ports.add(triangleOutput);
        ports.add(sawtoothOutput);

        setFrequency(frequency);
    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public void start() {
        fmFilter.start();
        squareOscillator.start();
        sawtoothOscillator.start();
        triangleOscillator.start();
    }

    @Override
    public void stop() {
        fmFilter.stop();
        squareOscillator.stop();
        sawtoothOscillator.stop();
        triangleOscillator.stop();
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

    @Override
    public String getName() {
        return "VCOA";
    }
}
