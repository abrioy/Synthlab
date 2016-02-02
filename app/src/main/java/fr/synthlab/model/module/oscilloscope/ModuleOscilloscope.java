package fr.synthlab.model.module.oscilloscope;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.*;
import fr.synthlab.model.module.Module;
import org.apache.log4j.Logger;
import fr.synthlab.model.module.port.Port;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;


public class ModuleOscilloscope implements Module {
    private static final Logger logger = Logger.getLogger(ModuleOscilloscope.class);

    private AudioScope scope;

    public ModuleOscilloscope(Synthesizer synth) {
        scope = new AudioScope(synth);
    }

    @Override
    public Collection<Port> getPorts() {
        return new ArrayList<>();
    }

    public void connect(UnitOutputPort output){
        scope.addProbe(output);
        scope.start();
        scope.setTriggerMode( AudioScope.TriggerMode.NORMAL );
    }

    @Override
    public void update() {

    }

    public JComponent getOscillatorJComponent() {
        return new JOscillatorComponent(scope);
    }

    class JOscillatorComponent extends JComponent
    {
        //private static final long serialVersionUID = -8315903842197137926L;
        //private ArrayList<UnitOscillator> oscillators = new ArrayList<UnitOscillator>();
        private LineOut lineOut;
        private Synthesizer synth;
        private JPanel oscPanel;
        private Multiply oscGain;
        private LinearRamp freqRamp;

        public JOscillatorComponent(AudioScope scope){
            synth = scope.getModel().getSynthesizer();

            // Use a multiplier for gain control and so we can hook up to the scope
            // from a single unit.
            synth.add(oscGain = new Multiply());
            oscGain.inputB.setup(0.02, 0.5, 1.0);
            oscGain.inputB.setName("Amplitude");

            synth.add(freqRamp = new LinearRamp());
            freqRamp.input.setup(50.0, 300.0, 20000.0);
            freqRamp.input.setName("Frequency");
            freqRamp.time.set(0.1);

            // Add an output so we can hear the oscillators.
            synth.add(lineOut = new LineOut());

            oscGain.output.connect(lineOut.input);

            setLayout( new BorderLayout() );

            scope = new AudioScope( synth );
            scope.addProbe( oscGain.output );
            scope.setTriggerMode( AudioScope.TriggerMode.NORMAL );
            scope.getView().setShowControls(false);
            scope.getView().setBorder(BorderFactory.createLineBorder(Color.RED));
            scope.getView().setBackground(Color.CYAN);
            scope.start();
            add(BorderLayout.CENTER, scope.getView());

            oscPanel = new JPanel();
            oscPanel.setLayout(new GridLayout(2, 5));

            oscPanel.validate();
            validate();

            // Create a square oscillator and connects it
            UnitOscillator osc = new TriangleOscillator();
            synth.add(osc);
            freqRamp.output.connect(osc.frequency);
            osc.amplitude.set(1.0);
            oscGain.inputA.disconnectAll(0);
            osc.output.connect(oscGain.inputA);

            // Start lineOut so it can pull data from other units.
            lineOut.start();

            // We only need to start the LineOut. It will pull data from the
            // oscillator.
            lineOut.start();
        }
    }
}
