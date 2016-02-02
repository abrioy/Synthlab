package fr.synthlab.view.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ToolboxController implements Initializable {
	private static final Logger logger = Logger.getLogger(ToolboxController.class);

	@FXML
	private Accordion toolbox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
