package fr.synthlab.view;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkbenchController implements Initializable {
	private static final Logger logger = Logger.getLogger(WorkbenchController.class);

	@FXML
	private StackPane workbench;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			workbench.getChildren().add(
					FXMLLoader.load(getClass().getResource("/gui/fxml/module/ViewModuleVCO.fxml"))
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
