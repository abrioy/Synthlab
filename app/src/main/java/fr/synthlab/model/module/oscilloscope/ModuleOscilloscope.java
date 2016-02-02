package fr.synthlab.model.module.oscilloscope;

import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.PassThrough;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;


public class ModuleOscilloscope implements Module {
    private static final Logger logger = Logger.getLogger(ModuleOscilloscope.class.getName());

    private AudioScope scope;
    private InputPort in;
    private PassThrough pt;
    private ArrayList<Port> ports = new ArrayList<>();

    // Ports
    private OutputPort out;

    public ModuleOscilloscope(Synthesizer synth) {
        scope = new AudioScope(synth);
        pt = new PassThrough();
        in = new InputPort("in", this, pt.input);
        ports.add(in);
        scope.setTriggerMode(AudioScope.TriggerMode.NORMAL);
        scope.addProbe(pt.output);
    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public void start() {
        scope.start();
    }

    @Override
    public void stop() {
        scope.stop();
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
