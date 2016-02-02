package fr.synthlab.model.module.oscilloscope;

import com.jsyn.Synthesizer;
import com.jsyn.ports.ConnectableOutput;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;


public class ModuleOscilloscope implements Module {
    private static final Logger logger = Logger.getLogger(ModuleOscilloscope.class);

    private AudioScope scope;

    // Ports
    private Collection<Port> ports;
    private OutputPort out;

    public ModuleOscilloscope(Synthesizer synth) {
        scope = new AudioScope(synth);

        ports = new ArrayList<Port>();
        ports.add(out);
    }

    @Override
    public Collection<Port> getPorts() {
        return new ArrayList<>();
    }

    public void connect(OutputPort output){
        scope.addProbe((UnitOutputPort) output.getOutput());
        scope.start();
        scope.setTriggerMode(AudioScope.TriggerMode.NORMAL);

        out = new OutputPort("out", this, (ConnectableOutput) output.getOutput());

    }

    @Override
    public void update() {

    }

    public JComponent getOscillatorJComponent() {
        return new JOscillatorComponent(scope);
    }

    class JOscillatorComponent extends JComponent
    {
        private JPanel oscPanel;

        public JOscillatorComponent(AudioScope scope){
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
