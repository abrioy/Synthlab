package fr.synthlab.view;


import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.out.ModuleOut;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class WorkbenchController implements Initializable {
	private static final Logger logger = Logger.getLogger(WorkbenchController.class.getName());

	@FXML
	private StackPane workbench;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Synthesizer synth = JSyn.createSynthesizer();

		ModuleVCOA vcoa = new ModuleVCOA(synth);
		// Add an output mixer.
		ModuleOut b = new ModuleOut(synth);

		ModuleOscilloscope oscillo = new ModuleOscilloscope(synth);

		synth.start();

		Port o1 = null;
		Port o2 = null;
		Port o3 = null;

		for (Port p : vcoa.getPorts()) {
			if (p.getName().equals("square")) {
				o1 = p;
				((OutputPort) p).connect(b.getInput());
				oscillo.getPort("in").connect((OutputPort) p);
			}
			if (p.getName().equals("triangle")) {
				o2 = p;
				((OutputPort) p).connect(b.getInput());
			}
			if (p.getName().equals("sawtooth")) {
				o3 = p;
				((OutputPort) p).connect(b.getInput());
			}
		}

		/*oscillo.connect((OutputPort) o3);

		b.start();
		workbench.getChildren().add(new ViewModuleOscillator(oscillo));

		final Port finalO = o1;
		Thread test = new Thread(() -> {
            try {
                Thread.sleep(5000);
                oscillo.connect((OutputPort) finalO);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

		test.start();*/

	}
}
