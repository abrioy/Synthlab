package fr.synthlab.view.module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ViewModuleFrame extends StackPane {
	private static final Logger logger = Logger.getLogger(ViewModuleFrame.class);

	public ViewModuleFrame() {
		super();

		this.getStyleClass().add("module-frame");
	}

	protected void loadFXML(String fxmlPath) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
		fxmlLoader.setController(this);

		try {
			Parent root = fxmlLoader.load();
			this.getChildren().add(root);

		} catch (IOException exception) {
			logger.error("Cannot load the specified FXML file: \""+fxmlPath+"\".");
			throw new RuntimeException(exception);
		}
	}
}
