package fr.synthlab.view;


import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.view.module.ViewModuleOscillator;
import fr.synthlab.view.module.ViewModuleVCO;
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
		//Synthesizer synth = JSyn.createSynthesizer();
		//ModuleOscilloscope osc = new ModuleOscilloscope(synth);
		//workbench.getChildren().add(new ViewModuleOscillator(osc));
	}
}
