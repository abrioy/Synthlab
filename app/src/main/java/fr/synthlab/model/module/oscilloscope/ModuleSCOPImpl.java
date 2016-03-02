package fr.synthlab.model.module.oscilloscope;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.scope.AudioScopeModel;
import com.jsyn.scope.AudioScopeProbe;
import com.jsyn.scope.WaveTraceModel;
import com.jsyn.scope.swing.AudioScopeProbeView;
import com.jsyn.scope.swing.ScopeControlPanel;
import com.jsyn.swing.ExponentialRangeModel;
import com.jsyn.unitgen.PassThrough;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Module oscilloscope to display the transmitted signal.
 * @see Module
 */
public class ModuleSCOPImpl implements ModuleSCOP {
    private static final Logger LOGGER
            = Logger.getLogger(ModuleSCOPImpl.class.getName());

    /**
     * Audio Scope.
     */
    private CustomAudioScope scope;

    /**
     * Input port.
     */
    private InputPort in;

    /**
     * Output port.
     */
    private OutputPort out;

    /**
     * All ports.
     */
    private ArrayList<Port> ports = new ArrayList<>();

    /**
     * Pass through.
     */
    private PassThrough pt;

    /**
     * Oscilloscope graphical (Swing) representation.
     */
    private JOscillatorComponent jOscillatorComponent;

    /**
     * Constructor.
     *
     * @param synth Synthesizer
     */
    public ModuleSCOPImpl(final Synthesizer synth) {
        scope = new CustomAudioScope(synth);
        pt = new PassThrough();
        in = new InputPort("in", this, pt.input);
        out = new OutputPort("out", this, pt.output);

        ports.add(in);
        ports.add(out);
        scope.setTriggerMode(AudioScope.TriggerMode.AUTO);
        scope.addProbe(pt.output);
        jOscillatorComponent = new JOscillatorComponent(scope);
    }

    @Override
    public final Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public final void start() {
        scope.start();
    }

    @Override
    public final void stop() {
        scope.stop();
    }

    @Override
    public void update() {
    }

    @Override
    public final void setScale(final int scale) {
        int s = Math.max(scale, 0);
        s = Math.min(s, 100);
        jOscillatorComponent.setScale(s);
    }

    @Override
    public final int getScale() {
        return jOscillatorComponent.getScale();
    }

    @Override
    public final ModuleType getType() {
        return ModuleType.SCOP;
    }

    @Override
    public final JComponent getOscillatorJComponent() {
        jOscillatorComponent.revalidate();
        jOscillatorComponent.repaint();
        return jOscillatorComponent;
    }

    /**
     * A JOscillatorComponent is a JComponent which shows the
     * wave happening inside scope.
     */
    class JOscillatorComponent extends JComponent {
        private JPanel oscPanel;
        private CustomAudioScope scope;

        JOscillatorComponent(final CustomAudioScope scopeInit) {
            scope = scopeInit;
            setLayout(new BorderLayout());
            add(BorderLayout.CENTER, scope.getView());

            oscPanel = new JPanel();
            oscPanel.setLayout(new GridLayout(2, 5));

            oscPanel.validate();
            validate();
        }

        /**
         * Sets the horizontal scale of the oscilloscope.
         *
         * @param scale An integer between 0 and 100
         */
        public void setScale(final int scale) {
            scope.getView().setScale(scale);
        }

        public int getScale() {
            return scope.getView().getScale();
        }
    }

    /**
     * *********************************************************
     * Below: Custom classes made from JSyn scope classes in order to
     * get a decent-looking oscillator.
     * <p>
     * These classes, slightly modified by us, are under the
     * following licence:
     * <p>
     * ***********************************************************
     * <p>
     * Copyright 2009 Phil Burk, Mobileer Inc
     * <p>
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     * <p>
     * http://www.apache.org/licenses/LICENSE-2.0
     * <p>
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     * <p>
     * ***********************************************************
     */

    private class CustomAudioScope {

        private CustomAudioScopeView audioScopeView = null;
        private AudioScopeModel audioScopeModel;

        CustomAudioScope(final Synthesizer synth) {
            audioScopeModel = new AudioScopeModel(synth);
        }

        public AudioScopeProbe addProbe(final UnitOutputPort output) {
            return addProbe(output, 0);
        }

        public AudioScopeProbe addProbe(
                final UnitOutputPort output, final int partIndex) {
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

        public void setTriggerMode(final AudioScope.TriggerMode triggerMode) {
            audioScopeModel.setTriggerMode(triggerMode);
        }

        public void setTriggerSource(final AudioScopeProbe probe) {
            audioScopeModel.setTriggerSource(probe);
        }

        public double getTriggerLevel() {
            return getModel().getTriggerModel()
                    .getLevelModel().getDoubleValue();
        }

        public void setTriggerLevel(final double level) {
            getModel().getTriggerModel().getLevelModel().setDoubleValue(level);
        }

        public void setViewMode(final AudioScope.ViewMode waveform) {
        }

    }

    private class CustomAudioScopeView extends JPanel {
        private static final long serialVersionUID = -7507986850757860853L;
        private AudioScopeModel audioScopeModel;
        private ArrayList<CustomAudioScopeProbeView> probeViews
                = new ArrayList<>();
        private CustomMultipleWaveDisplay multipleWaveDisplay;
        private boolean showControls = false;
        private ScopeControlPanel controlPanel = null;

        CustomAudioScopeView() {
        }

        private void setupGUI() {
            removeAll();
            setLayout(new BorderLayout());
            setOpaque(true);
            setBackground(Color.BLACK);
            multipleWaveDisplay = new CustomMultipleWaveDisplay();

            for (CustomAudioScopeProbeView probeView : probeViews) {
                multipleWaveDisplay.addWaveTrace(probeView.getWaveTraceView());
                probeView.getModel().setColor(
                        probeView.getWaveTraceView().getColor());
            }

            add(multipleWaveDisplay, BorderLayout.CENTER);

            setMinimumSize(new Dimension(475, 290));
            setPreferredSize(new Dimension(475, 290));
            setMaximumSize(new Dimension(475, 290));

            multipleWaveDisplay.revalidate();
            multipleWaveDisplay.repaint();
            repaint();
            revalidate();
        }

        public AudioScopeModel getModel() {
            return audioScopeModel;
        }

        public void setModel(final AudioScopeModel newAudioScopeModel) {
            audioScopeModel = newAudioScopeModel;
            // Create a view for each probe.
            probeViews.clear();
            for (AudioScopeProbe probeModel : audioScopeModel.getProbes()) {
                CustomAudioScopeProbeView audioScopeProbeView
                        = new CustomAudioScopeProbeView(probeModel);
                probeViews.add(audioScopeProbeView);
            }
            setupGUI();

            // Listener for signal change events.
            audioScopeModel.addChangeListener(e -> {
                multipleWaveDisplay.revalidate();
                multipleWaveDisplay.repaint();
            });

        }

        public AudioScopeProbeView[] getProbeViews() {
            return probeViews.toArray(
                    new AudioScopeProbeView[probeViews.size()]);
        }

        public void setScale(final int scale) {
            multipleWaveDisplay.setScale(scale);
        }

        public int getScale() {
            return multipleWaveDisplay.getScale();
        }
    }

    private class CustomMultipleWaveDisplay extends JPanel {
        private static final long serialVersionUID = -5157397030540800373L;

        private ArrayList<CustomWaveTraceView> waveTraceViews
                = new ArrayList<>();
        private Color[] defaultColors = {
                new Color(160, 230, 50),
                Color.BLUE,
                Color.RED,
                Color.BLACK,
                Color.MAGENTA,
                Color.GREEN,
                Color.ORANGE
        };

        CustomMultipleWaveDisplay() {
            setOpaque(true);
            setBackground(Color.BLACK);
        }

        public void addWaveTrace(final CustomWaveTraceView waveTraceView) {
            if (waveTraceView.getColor() == null) {
                waveTraceView.setColor(defaultColors[
                        waveTraceViews.size() % defaultColors.length]);
            }
            waveTraceViews.add(waveTraceView);
        }

        public void setScale(final int scale) {
            for (CustomWaveTraceView wave : waveTraceViews) {
                wave.setScale(scale);
            }
        }

        public int getScale() {
            return waveTraceViews.get(0).getScale(); // FIXME: Corentin
        }

        @Override
        public void paintComponent(final Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            for (CustomWaveTraceView waveTraceView : waveTraceViews) {
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

        CustomWaveTraceView(
                final JToggleButton.ToggleButtonModel autoButtonModel,
                final ExponentialRangeModel verticalRangeModel) {
            this.verticalScaleModel = verticalRangeModel;
            this.autoScaleButtonModel = autoButtonModel;
            this.scale = 4;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(final Color newColor) {
            color = newColor;
        }

        public ExponentialRangeModel getVerticalRangeModel() {
            return verticalScaleModel;
        }

        public JToggleButton.ToggleButtonModel getAutoButtonModel() {
            return autoScaleButtonModel;
        }

        public void setModel(final WaveTraceModel newWaveTraceModel) {
            waveTraceModel = newWaveTraceModel;
        }

        public int convertRealToY(final double r) {
            return centerY - (int) (yScalar * r);
        }

        public void drawWave(
                final Graphics g, final int width, final int height) {
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
                    ((Graphics2D) g).setRenderingHint(
                            RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
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
        private void autoScaleRange(final double sampleMax) {
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

        public void setScale(final int newScale) {
            scale = newScale;
        }

        public int getScale() {
            return scale;
        }

    }

    private class CustomAudioScopeProbeView {
        private AudioScopeProbe probeModel;
        private CustomWaveTraceView waveTrace;

        CustomAudioScopeProbeView(final AudioScopeProbe probeModelInit) {
            probeModel = probeModelInit;
            waveTrace = new CustomWaveTraceView(
                    probeModel.getAutoScaleButtonModel(),
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
