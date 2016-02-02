package fr.synthlab.model.module.oscilloscope;

import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.scope.AudioScopeModel;
import com.jsyn.scope.AudioScopeProbe;
import com.jsyn.scope.swing.*;
import com.jsyn.unitgen.PassThrough;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;
import com.jsyn.ports.UnitOutputPort;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jsyn.scope.AudioScopeModel;
import com.jsyn.scope.AudioScopeProbe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;


public class ModuleOscilloscope implements Module {
    private static final Logger logger = Logger.getLogger(ModuleOscilloscope.class.getName());

    private CustomAudioScope scope;
    private InputPort in;
    private PassThrough pt;
    private ArrayList<Port> ports = new ArrayList<>();

    // Ports
    private OutputPort out;

    public ModuleOscilloscope(Synthesizer synth) {
        scope = new CustomAudioScope(synth);
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

        public JOscillatorComponent(CustomAudioScope scope){
            setLayout( new BorderLayout() );

            scope.getView().setBorder(BorderFactory.createLineBorder(Color.BLACK));
            scope.getView().setOpaque(true);
            scope.getView().setPreferredSize(new Dimension(300,300));
            setBackground(Color.BLACK);
            for (AudioScopeProbeView a : scope.getView().getProbeViews()) {
                a.getWaveTraceView().setColor(Color.GREEN);
            }
            add(BorderLayout.CENTER, scope.getView());

            oscPanel = new JPanel();
            oscPanel.setLayout(new GridLayout(2, 5));

            oscPanel.validate();
            validate();
        }
    }


    /*************************
     * Below: Custom classes made from JSyn scope classes in order to
     * get a decent-looking oscillator.
     */

    public class CustomAudioScope {

        private AudioScopeView audioScopeView = null;
        private AudioScopeModel audioScopeModel;

        public CustomAudioScope(Synthesizer synth) {
            audioScopeModel = new AudioScopeModel(synth);
        }

        public AudioScopeProbe addProbe(UnitOutputPort output) {
            return addProbe(output, 0);
        }

        public AudioScopeProbe addProbe(UnitOutputPort output, int partIndex) {
            return audioScopeModel.addProbe(output, partIndex);
        }

        public void start() {
            audioScopeModel.start();
        }

        public void stop() {
            audioScopeModel.stop();
        }

        public AudioScopeModel getModel() {
            return audioScopeModel;
        }

        public AudioScopeView getView() {
            if (audioScopeView == null) {
                audioScopeView = new AudioScopeView();
                audioScopeView.setModel(audioScopeModel);
            }
            return audioScopeView;
        }

        public void setTriggerMode(AudioScope.TriggerMode triggerMode) {
            audioScopeModel.setTriggerMode(triggerMode);
        }

        public void setTriggerSource(AudioScopeProbe probe) {
            audioScopeModel.setTriggerSource(probe);
        }

        public void setTriggerLevel(double level) {
            getModel().getTriggerModel().getLevelModel().setDoubleValue(level);
        }

        public double getTriggerLevel() {
            return getModel().getTriggerModel().getLevelModel().getDoubleValue();
        }

        public void setViewMode(AudioScope.ViewMode waveform) {
        }

    }

    public class CustomAudioScopeView extends JPanel {
        private static final long serialVersionUID = -7507986850757860853L;
        private AudioScopeModel audioScopeModel;
        private ArrayList<AudioScopeProbeView> probeViews = new ArrayList<AudioScopeProbeView>();
        private CustomMultipleWaveDisplay multipleWaveDisplay;
        private boolean showControls = false;
        private ScopeControlPanel controlPanel = null;

        public CustomAudioScopeView() {
            setBackground(Color.GREEN);
        }

        public void setModel(AudioScopeModel audioScopeModel) {
            this.audioScopeModel = audioScopeModel;
            // Create a view for each probe.
            probeViews.clear();
            for (AudioScopeProbe probeModel : audioScopeModel.getProbes()) {
                AudioScopeProbeView audioScopeProbeView = new AudioScopeProbeView(probeModel);
                probeViews.add(audioScopeProbeView);
            }
            setupGUI();

            // Listener for signal change events.
            audioScopeModel.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    multipleWaveDisplay.repaint();
                }
            });

        }

        private void setupGUI() {
            removeAll();
            setLayout(new BorderLayout());
            multipleWaveDisplay = new CustomMultipleWaveDisplay();

            for (AudioScopeProbeView probeView : probeViews) {
                multipleWaveDisplay.addWaveTrace(probeView.getWaveTraceView());
                probeView.getModel().setColor(probeView.getWaveTraceView().getColor());
            }

            add(multipleWaveDisplay, BorderLayout.CENTER);

            setMinimumSize(new Dimension(400, 200));
            setPreferredSize(new Dimension(600, 250));
            setMaximumSize(new Dimension(1200, 300));
        }

        public AudioScopeModel getModel() {
            return audioScopeModel;
        }

        public AudioScopeProbeView[] getProbeViews() {
            return probeViews.toArray(new AudioScopeProbeView[0]);
        }

    }

    public class CustomMultipleWaveDisplay extends JPanel {
        private static final long serialVersionUID = -5157397030540800373L;

        private ArrayList<WaveTraceView> waveTraceViews = new ArrayList<WaveTraceView>();
        private Color[] defaultColors = {
                Color.BLUE, Color.RED, Color.BLACK, Color.MAGENTA, Color.GREEN, Color.ORANGE
        };

        public CustomMultipleWaveDisplay() {
            setOpaque(true);
            setBackground(Color.BLACK);
        }

        public void addWaveTrace(WaveTraceView waveTraceView) {
            if (waveTraceView.getColor() == null) {
                waveTraceView.setColor(defaultColors[waveTraceViews.size() % defaultColors.length]);
            }
            waveTraceViews.add(waveTraceView);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            for (WaveTraceView waveTraceView : waveTraceViews.toArray(new WaveTraceView[0])) {
                waveTraceView.drawWave(g, width, height);
            }
        }
    }
}
