package fr.synthlab.model.module.oscilloscope;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.*;
import com.jsyn.scope.swing.AudioScopeProbeView;
import com.jsyn.scope.swing.ScopeControlPanel;
import com.jsyn.swing.ExponentialRangeModel;
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
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
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

    public void setScale(int scale) {
        scope.getView().setScale(scale);
    }

    /**
     * Getter on the Scope panel.
     * @return JComponent that displays the scope
     */
    public JComponent getOscillatorJComponent() {
        return new JOscillatorComponent(scope);
    }

    /**
     * A JOscillatorComponent is a JComponent which shows the
     * wave happening inside scope.
     */
    class JOscillatorComponent extends JComponent
    {
        private JPanel oscPanel;
        private CustomAudioScope scope;

        public JOscillatorComponent(CustomAudioScope scope){
            this.scope = scope;
            setLayout(new BorderLayout());
            add(BorderLayout.CENTER, this.scope.getView());

            oscPanel = new JPanel();
            oscPanel.setLayout(new GridLayout(2, 5));

            oscPanel.validate();
            validate();
        }

        /**
         * Sets the horizontal scale of the oscilloscope
         * @param scale An integer between 0 and 100
         */
        public void setScale(int scale) {
            scope.getView().setScale(scale);
        }
    }

    /** **********************************************************
     * Below: Custom classes made from JSyn scope classes in order to
     * get a decent-looking oscillator.
     *
     * These classes, slightly modified by us, are under the
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

        public void setTriggerLevel(double level) {
            getModel().getTriggerModel().getLevelModel().setDoubleValue(level);
        }

        public double getTriggerLevel() {
            return getModel().getTriggerModel().getLevelModel().getDoubleValue();
        }

        public void setViewMode(AudioScope.ViewMode waveform) {
        }

    }

    private class CustomAudioScopeView extends JPanel {
        private static final long serialVersionUID = -7507986850757860853L;
        private AudioScopeModel audioScopeModel;
        private ArrayList<CustomAudioScopeProbeView> probeViews = new ArrayList<CustomAudioScopeProbeView>();
        private CustomMultipleWaveDisplay multipleWaveDisplay;
        private boolean showControls = false;
        private ScopeControlPanel controlPanel = null;

        public CustomAudioScopeView() {
        }

        public void setModel(AudioScopeModel audioScopeModel) {
            this.audioScopeModel = audioScopeModel;
            // Create a view for each probe.
            probeViews.clear();
            for (AudioScopeProbe probeModel : audioScopeModel.getProbes()) {
                CustomAudioScopeProbeView audioScopeProbeView = new CustomAudioScopeProbeView(probeModel);
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
            setOpaque(true);
            setBackground(Color.BLACK);
            multipleWaveDisplay = new CustomMultipleWaveDisplay();

            for (CustomAudioScopeProbeView probeView : probeViews) {
                multipleWaveDisplay.addWaveTrace(probeView.getWaveTraceView());
                probeView.getModel().setColor(probeView.getWaveTraceView().getColor());
            }

            add(multipleWaveDisplay, BorderLayout.CENTER);

            setMinimumSize(new Dimension(200, 200));
            setPreferredSize(new Dimension(500, 300));
            setMaximumSize(new Dimension(500, 500));
        }

        public AudioScopeModel getModel() {
            return audioScopeModel;
        }

        public AudioScopeProbeView[] getProbeViews() {
            return probeViews.toArray(new AudioScopeProbeView[0]);
        }

        public void setScale (int scale) {
            multipleWaveDisplay.setScale(scale);
        }

    }

    private class CustomMultipleWaveDisplay extends JPanel {
        private static final long serialVersionUID = -5157397030540800373L;

        private ArrayList<CustomWaveTraceView> waveTraceViews = new ArrayList<CustomWaveTraceView>();
        private Color[] defaultColors = {
                new Color(160, 230, 50), Color.BLUE, Color.RED, Color.BLACK, Color.MAGENTA, Color.GREEN, Color.ORANGE
        };

        public CustomMultipleWaveDisplay() {
            setOpaque(true);
            setBackground(Color.BLACK);
        }

        public void addWaveTrace(CustomWaveTraceView waveTraceView) {
            if (waveTraceView.getColor() == null) {
                waveTraceView.setColor(defaultColors[waveTraceViews.size() % defaultColors.length]);
            }
            waveTraceViews.add(waveTraceView);
        }

        public void setScale(int scale) {
            for (CustomWaveTraceView wave : waveTraceViews) {
                wave.setScale(scale);
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            for (CustomWaveTraceView waveTraceView : waveTraceViews.toArray(new CustomWaveTraceView[0])) {
                waveTraceView.drawWave(g, width, height);
            }
        }
    }

    private class CustomWaveTraceView {
        private static final double AUTO_DECAY = 0.95;
        private WaveTraceModel waveTraceModel;
        private Color color;
        private ExponentialRangeModel verticalScaleModel;
        private JToggleButton.ToggleButtonModel autoScaleButtonModel;

        private double xScaler;
        private double yScalar;
        private int centerY;

        // Horizontal scale parameter
        private int scale;

        public CustomWaveTraceView(JToggleButton.ToggleButtonModel autoButtonModel, ExponentialRangeModel verticalRangeModel) {
            this.verticalScaleModel = verticalRangeModel;
            this.autoScaleButtonModel = autoButtonModel;
            this.scale = 4;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public ExponentialRangeModel getVerticalRangeModel() {
            return verticalScaleModel;
        }

        public JToggleButton.ToggleButtonModel getAutoButtonModel() {
            return autoScaleButtonModel;
        }

        public void setModel(WaveTraceModel waveTraceModel) {
            this.waveTraceModel = waveTraceModel;
        }

        public int convertRealToY(double r) {
            return centerY - (int) (yScalar * r);
        }

        public void drawWave(Graphics g, int width, int height) {
            double sampleMax = 0.0;
            double sampleMin = 0.0;
            g.setColor(color);
            int numSamples = scale * 4 + 50;
            //int numSamples = waveTraceModel.getVisibleSize();
            if (numSamples > 0) {
                xScaler = (double) width / numSamples;
                // Scale by 0.5 because it is bipolar.
                yScalar = 0.5 * height / verticalScaleModel.getDoubleValue();
                centerY = height / 2;

                // Calculate position of first point.
                int x1 = 0;
                int offset = waveTraceModel.getStartIndex();
                double value = waveTraceModel.getSample(offset);
                int y1 = convertRealToY(value);

                // Draw lines to remaining points.
                for (int i = 1; i < numSamples; i++) {
                    int x2 = (int) (i * xScaler);
                    value = waveTraceModel.getSample(offset + i);
                    int y2 = convertRealToY(value);
                    ((Graphics2D) g).setStroke(new BasicStroke(2));
                    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g.drawLine(x1, y1, x2, y2);
                    x1 = x2;
                    y1 = y2;
                    // measure min and max for auto
                    if (value > sampleMax) {
                        sampleMax = value;
                    } else if (value < sampleMin) {
                        sampleMin = value;
                    }
                }

                autoScaleRange(sampleMax);
            }
        }

        // Autoscale the vertical range.
        private void autoScaleRange(double sampleMax) {
            if (autoScaleButtonModel.isSelected()) {
                double scaledMax = sampleMax * 1.1;
                double current = verticalScaleModel.getDoubleValue();
                if (scaledMax > current) {
                    verticalScaleModel.setDoubleValue(scaledMax);
                } else {
                    double decayed = current * AUTO_DECAY;
                    if (decayed > verticalScaleModel.getMinimum()) {
                        if (scaledMax < decayed) {
                            verticalScaleModel.setDoubleValue(decayed);
                        }
                    }
                }
            }
        }

        public void setScale(int scale) {
            this.scale = scale;
        }

    }

    private class CustomAudioScopeProbeView {
        private AudioScopeProbe probeModel;
        private CustomWaveTraceView waveTrace;

        public CustomAudioScopeProbeView(AudioScopeProbe probeModel) {
            this.probeModel = probeModel;
            waveTrace = new CustomWaveTraceView(probeModel.getAutoScaleButtonModel(),
                    probeModel.getVerticalScaleModel());
            waveTrace.setModel(probeModel.getWaveTraceModel());
        }

        public CustomWaveTraceView getWaveTraceView() {
            return waveTrace;
        }

        public AudioScopeProbe getModel() {
            return probeModel;
        }

    }
}
