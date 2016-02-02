package fr.synthlab.view.controller;


import fr.synthlab.view.module.ViewModuleVCO;
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

		workbench.getChildren().add(new ViewModuleVCO());
		//workbench.getChildren().add(new Plug(50,0));

		//Synthesizer synth = JSyn.createSynthesizer();
		//ModuleOscilloscope osc = new ModuleOscilloscope(synth);
		//workbench.getChildren().add(new ViewModuleOscillator(osc));
	}
}