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
		// Add an output mixer.
		ModuleOut b = new ModuleOut(synth);

		ModuleOscilloscope oscillo = new ModuleOscilloscope(synth);

		synth.start();

		OutputPort p = (OutputPort) vcoa.getPort("square");
		p.connect(b.getInput());
		InputPort inOsc = (InputPort) oscillo.getPort("in");
		p.connect(inOsc);

		vcoa.start();
		oscillo.start();
		b.start();
		this.getChildren().add(new ViewModuleOscillator(oscillo));
	}
}