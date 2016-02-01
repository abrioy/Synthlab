package fr.synthlab.view.module;

import javafx.fxml.FXMLLoader;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ViewModuleVCO extends ViewModuleFrame {
	private static final Logger logger = Logger.getLogger(ViewModuleVCO.class);

	public ViewModuleVCO() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/fxml/module/ViewModuleVCO.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
