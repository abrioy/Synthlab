package fr.synthlab.model.module.oscilloscope;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.scope.AudioScopeModel;
import com.jsyn.scope.AudioScopeProbe;
import com.jsyn.scope.swing.AudioScopeProbeView;
import com.jsyn.scope.swing.ScopeControlPanel;
import com.jsyn.scope.swing.WaveTraceView;
import com.jsyn.unitgen.PassThrough;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Module oscilloscope to display the transmitted signal.
 * @author Anthony Cobac & Corentin Beauce
 * @see Module
 */
public class ModuleOscilloscope implements Module {
    private static final Logger logger = Logger.getLogger(ModuleOscilloscope.class.getName());

    /**
     * Audio Scope
     */
    private CustomAudioScope scope;

    /**
     * Input port
     */
    private InputPort in;

    /**
     * Output port
     */
    private OutputPort out;

    /**
     * All ports
     */
    private ArrayList<Port> ports = new ArrayList<>();

    /**
     * Pass through
     */
    private PassThrough pt;

    /**
     * Constructor
     * @param synth Synthesizer
     */
    public ModuleOscilloscope(Synthesizer synth) {
        scope = new CustomAudioScope(synth);
        pt = new PassThrough();
        in = new InputPort("in", this, pt.input);
        out = new OutputPort("out", this, pt.output);

        ports.add(in);
        ports.add(out);
        scope.setTriggerMode(AudioScope.TriggerMode.NORMAL);
        scope.addProbe(pt.output);
        out = new OutputPort("out", this, pt.output);
        ports.add(out);
    }

    /**
     * Getter on ports input and output.
     * @return Scope port
     */
    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    /**
     * Start scope
     */
    @Override
    public void start() {
        scope.start();
    }

    /**
     * Stop scope
     */
    @Override
    public void stop() {
        scope.stop();
    }

    /**
     * Inherit method.
     */
    @Override
    public void update() {

    }

    @Override
    public String getName() {
        return "OSCILLOSCOPE";
    }

    /**
     * Getter on the Scope panel.
     * @return JComponent that displays the scope
     */
    public JComponent getOscillatorJComponent() {
        return new JOscillatorComponent(scope);
    }

    /**
     * JComponent class
     */
    class JOscillatorComponent extends JComponent
    {
        private JPanel oscPanel;

        public JOscillatorComponent(CustomAudioScope scope){
            setLayout( new BorderLayout() );

            scope.getView().setBorder(BorderFactory.createLineBorder(Color.BLACK));
            scope.getView().setOpaque(true);
            scope.getView().setBackground(Color.BLACK);
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


    /** **********************************************************
     * Below: Custom classes made from JSyn scope classes in order to
     * get a decent-looking oscillator.
     *
     * These three classes, slightly modified by us, are under the
     * following licence:
     *
     * ***********************************************************
     *
     * Copyright 2009 Phil Burk, Mobileer Inc
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *     http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     *
     * ***********************************************************
     *
     */

    private class CustomAudioScope {

        private CustomAudioScopeView audioScopeView = null;
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

        public CustomAudioScopeView getView() {
            if (audioScopeView == null) {
                audioScopeView = new CustomAudioScopeView();
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

        public double getTriggerLevel() {
            return getModel().getTriggerModel().getLevelModel().getDoubleValue();
        }

        public void setTriggerLevel(double level) {
            getModel().getTriggerModel().getLevelModel().setDoubleValue(level);
        }

        public void setViewMode(AudioScope.ViewMode waveform) {
        }

    }

    private class CustomAudioScopeView extends JPanel {
        private static final long serialVersionUID = -7507986850757860853L;
        private AudioScopeModel audioScopeModel;
        private ArrayList<AudioScopeProbeView> probeViews = new ArrayList<AudioScopeProbeView>();
        private CustomMultipleWaveDisplay multipleWaveDisplay;
        private boolean showControls = false;
        private ScopeControlPanel controlPanel = null;

        public CustomAudioScopeView() {
            setOpaque(true);
            setBackground(Color.YELLOW);
        }

        private void setupGUI() {
            removeAll();
            setLayout(new BorderLayout());
            setOpaque(true);
            setBackground(Color.BLACK);
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

        public AudioScopeProbeView[] getProbeViews() {
            return probeViews.toArray(new AudioScopeProbeView[0]);
        }

    }

    private class CustomMultipleWaveDisplay extends JPanel {
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
            super.setOpaque(true);
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            for (WaveTraceView waveTraceView : waveTraceViews.toArray(new WaveTraceView[0])) {
                waveTraceView.drawWave(g, width, height);
            }
        }
    }
}
