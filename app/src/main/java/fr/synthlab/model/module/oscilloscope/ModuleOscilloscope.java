package fr.synthlab.model.module.oscilloscope;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LinearRamp;
import com.jsyn.unitgen.Multiply;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.Port;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;


public class ModuleOscilloscope implements Module {
    private static final Logger logger = Logger.getLogger(ModuleOscilloscope.class.getName());

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
        private Synthesizer synth;
        private JPanel oscPanel;
        private Multiply oscGain;
        private LinearRamp freqRamp;

        public JOscillatorComponent(AudioScope scope){
            synth = scope.getModel().getSynthesizer();

            synth.add(oscGain = new Multiply());
            oscGain.inputB.setup(0.02, 0.5, 1.0);
            oscGain.inputB.setName("Amplitude");

            synth.add(freqRamp = new LinearRamp());
            freqRamp.input.setup(50.0, 300.0, 20000.0);
            freqRamp.input.setName("Frequency");
            freqRamp.time.set(0.1);

            setLayout( new BorderLayout() );

            scope.getView().setBorder(BorderFactory.createLineBorder(Color.RED));
            scope.getView().setBackground(Color.CYAN);
            add(BorderLayout.CENTER, scope.getView());

            oscPanel = new JPanel();
            oscPanel.setLayout(new GridLayout(2, 5));

            oscPanel.validate();
            validate();
        }
    }
}
