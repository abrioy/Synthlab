package fr.synthlab.view;


import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.out.ModuleOut;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;
import fr.synthlab.model.module.vcoa.VCOA;
import fr.synthlab.view.module.ViewModuleOscillator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkbenchController implements Initializable {
	private static final Logger logger = Logger.getLogger(WorkbenchController.class);

	@FXML
	private StackPane workbench;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Synthesizer synth = JSyn.createSynthesizer();

		VCOA vcoa = new VCOA(synth);
		// Add an output mixer.
		ModuleOut b = new ModuleOut(synth);

		ModuleOscilloscope oscillo = new ModuleOscilloscope(synth);

		synth.start();

		for (Port p : vcoa.getPorts()) {
			if (p.getName().equals("square")) {
				((OutputPort) p).connect(b.getInput());
				oscillo.connect((OutputPort) p);
			}
		}

		b.start();
		workbench.getChildren().add(new ViewModuleOscillator(oscillo));
	}
}
