package fr.synthlab.view.module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import java.util.logging.Logger;

import java.io.IOException;

public class ViewModuleFrame extends StackPane {
	private static final Logger logger = Logger.getLogger(ViewModuleFrame.class.getName());

	double mouseX ;
	double mouseY ;

	public ViewModuleFrame() {
		super();

		this.getStyleClass().add("module-frame");
		this.getStylesheets().add("/gui/fxml/style/Module.css");

		// Making the frame draggable
		this.setOnMousePressed(event -> {
			mouseX = event.getSceneX() ;
			mouseY = event.getSceneY() ;
		});

		this.setOnMouseDragged(event -> {
			double deltaX = event.getSceneX() - mouseX ;
			double deltaY = event.getSceneY() - mouseY ;
			this.relocate(getLayoutX() + deltaX, getLayoutY() + deltaY);
			mouseX = event.getSceneX() ;
			mouseY = event.getSceneY() ;
			this.setCursor(Cursor.MOVE);
		});

		this.setOnMouseEntered(mouseEvent -> this.setCursor(Cursor.HAND));
		this.setOnMouseReleased(mouseEvent -> this.setCursor(Cursor.HAND));

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
