package fr.synthlab.view.module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.logging.Logger;

public abstract class ViewModule extends StackPane {
	private static final Logger logger = Logger.getLogger(ViewModule.class.getName());

	public ViewModule() {
		super();

		this.getStyleClass().add("module-frame");
		this.getStylesheets().add("/gui/fxml/style/Module.css");

	}

	protected void loadFXML(String fxmlPath) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
		fxmlLoader.setController(this);

		try {
			Parent root = fxmlLoader.load();
			this.getChildren().add(root);

		} catch (IOException exception) {
			logger.severe("Cannot load the specified FXML file: \""+fxmlPath+"\".");
			throw new RuntimeException(exception);
		}
	}


}
