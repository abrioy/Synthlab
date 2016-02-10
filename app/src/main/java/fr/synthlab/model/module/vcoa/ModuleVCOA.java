package fr.synthlab.model.module.vcoa;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.*;
import fr.synthlab.model.filter.FmFilter;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * VCO module ( Voltage Control Oscillator ) for generating a periodic
 * signal whose shape can be selected and the frequency can be controlled by another signal
 */
public class ModuleVCOA implements Module {
	private static final Logger logger = Logger.getLogger(ModuleVCOA.class.getName());

    /**
     * The list of port of the VCO module
     */
    private Collection<Port> ports = new ArrayList<>();

    /**
     * The frequency f0 of the VCO
     */
    private double frequency = 450;

    /**
     * Filter modulator
     */
    private FmFilter fmFilter = new FmFilter(frequency);

    /**
     * Square oscillator
     */
    private SquareOscillator squareOscillator = new SquareOscillator();

    /**
     * Triangle Oscillator
     */
    private TriangleOscillator triangleOscillator = new TriangleOscillator();

    /**
     * Sawtooth Oscillator
     */
    private SawtoothOscillator sawtoothOscillator = new SawtoothOscillator();

    /**
     * Sin Oscillator
     */
    private SineOscillator sineOscillator = new SineOscillator();

    /**
     * Filter modulator input port
     */
    private InputPort fmInput;


    private OutputPort outputPort;

    private PassThrough passThrough = new PassThrough();

    private ShapeEnum shape;


    /**
     * Constructor
     *
     * @param synthesizer
     */
    public ModuleVCOA(Synthesizer synthesizer) {
        synthesizer.add(squareOscillator);
        synthesizer.add(triangleOscillator);
        synthesizer.add(sawtoothOscillator);
        synthesizer.add(sineOscillator);
        synthesizer.add(fmFilter);
        synthesizer.add(passThrough);

        fmInput = new InputPort("fm", this, fmFilter.input);
        outputPort = new OutputPort("out", this, passThrough.output);

        ports.add(fmInput);
        ports.add(outputPort);

        triangleOscillator.output.connect(passThrough.input);

        // Initialize the frequency of the fm filter and the 3 oscillators
        setFrequency(frequency);
    }

    /**
     *
     * @return the list of port of the VCO
     */
    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    /**
     * Start the VCO
     */
    @Override
    public void start() {
        fmFilter.start();
        squareOscillator.start();
        sawtoothOscillator.start();
        triangleOscillator.start();
        passThrough.start();
    }

    /**
     * Stop the VCO
     */
    @Override
    public void stop() {
        fmFilter.stop();
        squareOscillator.stop();
        sawtoothOscillator.stop();
        triangleOscillator.stop();
        sineOscillator.stop();
        passThrough.stop();
    }

    /**
     *
     * @return the frequency f0 of the VCO
     */
    public double getFrequency() {
        return frequency;
    }

    /**
     * set the frequency of the VCO
     * @param frequency
     */
    public void setFrequency(double frequency) {
        this.frequency = frequency;
        fmFilter.setf0(frequency);

        if (fmInput.getConnected() == null) {
            squareOscillator.frequency.set(frequency);
            triangleOscillator.frequency.set(frequency);
            sawtoothOscillator.frequency.set(frequency);
            sineOscillator.frequency.set(frequency);
        }
    }

    /**
     * This method is called by the input port fm of the VCO when its state has changed
     * When nothing is connected to the input port of fm, the 3 oscillators has the same frequency f0
     * When something is connected to the input port of fm, we connect the output port of fm filter
     * to input port of each oscillator
     *
     */
    @Override
    public void update() {
        if (fmInput.getConnected() == null) {
            fmFilter.output.disconnectAll();
            squareOscillator.frequency.set(frequency);
            triangleOscillator.frequency.set(frequency);
            sawtoothOscillator.frequency.set(frequency);
            sineOscillator.frequency.set(frequency);
        } else {
            fmFilter.output.connect(squareOscillator.frequency);
            fmFilter.output.connect(triangleOscillator.frequency);
            fmFilter.output.connect(sawtoothOscillator.frequency);
            fmFilter.output.connect(sineOscillator.frequency);
        }
    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.VCOA;
    }


    public ShapeEnum getShape() {
        return shape;
    }

    public void setShape(ShapeEnum shape) {
        this.shape = shape;

        switch (shape) {
            case TRIANGLE:
                passThrough.input.disconnectAll();
                triangleOscillator.output.connect(passThrough.input);
                break;
            case SQUARE:
                passThrough.input.disconnectAll();
                squareOscillator.output.connect(passThrough.input);
                break;
            case SAWTOOTH:
                passThrough.input.disconnectAll();
                sawtoothOscillator.output.connect(passThrough.input);
                break;
            case SINE:
                passThrough.input.disconnectAll();
                sineOscillator.output.connect(passThrough.input);
                break;
        }
    }


}
