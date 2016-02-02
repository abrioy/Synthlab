package fr.synthlab.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.*;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;

/**
 * Created by corentin on 01/02/16.
 */
public class OscillatorPane extends Pane {
    SwingNode swingNode;

    public OscillatorPane() {
		super();
		swingNode = new SwingNode();
		JOscillatorComponent osc = new JOscillatorComponent();
		osc.start();
        swingNode.setContent(osc);
        this.getChildren().add(swingNode);
	}
}

class JOscillatorComponent extends JComponent
{
	private static final long serialVersionUID = -8315903842197137926L;
	private Synthesizer synth;
	private ArrayList<UnitOscillator> oscillators = new ArrayList<UnitOscillator>();
	private LineOut lineOut;
	private AudioScope scope;
	private JPanel oscPanel;
	private Multiply oscGain;
	private ButtonGroup buttonGroup;
	private LinearRamp freqRamp;

	public JOscillatorComponent()
	{
		/*
		setSize(640, 500);
		setVisible(true);
		validate();
		*/
	}

	private void setupGUI()
	{
		setLayout( new BorderLayout() );

		scope = new AudioScope( synth );
		scope.addProbe( oscGain.output );
		scope.setTriggerMode( AudioScope.TriggerMode.NORMAL );
		//scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.0001 );
		// Turn off the gain and trigger control GUI.
		scope.getView().setShowControls(false );
		scope.start();
		add(BorderLayout.CENTER, scope.getView());

		oscPanel = new JPanel();
		oscPanel.setLayout(new GridLayout(2, 5));

		oscPanel.validate();
		validate();
	}

	public void start()
	{
		synth = JSyn.createSynthesizer();

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

		setupGUI();

		// Create a square oscillator and connects it
		UnitOscillator osc = new TriangleOscillator();
		synth.add( osc );
		freqRamp.output.connect( osc.frequency );
		osc.amplitude.set( 1.0 );
		oscGain.inputA.disconnectAll(0);
		osc.output.connect(oscGain.inputA);

		// Start synthesizer using default stereo output at 44100 Hz.
		synth.start();
		// Start lineOut so it can pull data from other units.
		lineOut.start();

		// We only need to start the LineOut. It will pull data from the
		// oscillator.
		lineOut.start();
	}

	public void stop()
	{
		scope.stop();
		synth.stop();
	}

}

