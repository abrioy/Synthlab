package fr.synthlab.view;


import fr.synthlab.view.module.ViewModuleVCO;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

public class Workbench extends Pane {
	private static final Logger logger = Logger.getLogger(Workbench.class);

	public Workbench() {

		ViewModuleVCO module = new ViewModuleVCO();
		this.getChildren().add(module);
		//Synthesizer synth = JSyn.createSynthesizer();
		//ModuleOscilloscope osc = new ModuleOscilloscope(synth);
		//workbench.getChildren().add(new ViewModuleOscillator(osc));
	}
}
