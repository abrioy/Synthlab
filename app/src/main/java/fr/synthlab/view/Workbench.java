package fr.synthlab.view;


import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.out.ModuleOut;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import fr.synthlab.view.module.ViewModuleOscillator;
import fr.synthlab.view.module.ViewModuleVCO;
import javafx.scene.layout.Pane;

import java.util.Scanner;
import java.util.logging.Logger;

public class Workbench extends Pane {
	private static final Logger logger = Logger.getLogger(Workbench.class.getName());

	public Workbench() {

		ViewModuleVCO module = new ViewModuleVCO();
		this.getChildren().add(module);
		//Synthesizer synth = JSyn.createSynthesizer();
		//ModuleOscilloscope osc = new ModuleOscilloscope(synth);
		//workbench.getChildren().add(new ViewModuleOscillator(osc));

		Synthesizer synth = JSyn.createSynthesizer();

		ModuleVCOA vcoa = new ModuleVCOA(synth);
		ModuleVCOA vcoa2 = new ModuleVCOA(synth);
		vcoa2.setFrequency(1);

		// Add an output mixer.
		ModuleOut sound = new ModuleOut(synth);

		ModuleOscilloscope oscillo = new ModuleOscilloscope(synth);

		synth.start();

		OutputPort squarePort = (OutputPort) vcoa.getPort("square");

		InputPort inOsc = (InputPort) oscillo.getPort("in");
		OutputPort outOsc = (OutputPort) oscillo.getPort("out");
		InputPort fm1 = (InputPort) vcoa.getPort("fm");
		OutputPort trianglePort2 = (OutputPort) vcoa2.getPort("square");

		// Connect square output to oscillo in
		squarePort.connect(inOsc);

		// Connect oscillo out to sound
		outOsc.connect(sound.getPort("in"));

		// Connect 2nd VCOA output to 1st VCOA input
		trianglePort2.connect(fm1);

		vcoa.start();
		vcoa2.start();
		oscillo.start();
		sound.start();
		this.getChildren().add(new ViewModuleOscillator(oscillo));

		Scanner sc = new Scanner(System.in);
		Thread t = new Thread(() -> {
			while (true) {
				String f = sc.next();
				String[] res = f.split("/");
				if (res[0].equals("1")) {
					vcoa.setFrequency(Integer.parseInt(res[1]));
				} else if (res[0].equals("2")) {
					vcoa2.setFrequency(Integer.parseInt(res[1]));
				}
			}
		});

		t.start();


	}
}
