package fr.synthlab.view.module;

import fr.synthlab.model.module.Module;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Plug;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.logging.Logger;

public abstract class ViewModule extends Pane {
	private static final Logger logger = Logger.getLogger(ViewModule.class.getName());

	private Workbench workbench;
	private Module module;
	private AnchorPane topPane;
	private Label moduleName;
	private Button closeButton;

	public ViewModule(Workbench workbench) {
	
		super();

		this.workbench = workbench;

		this.getStyleClass().add("module-frame");
		this.getStylesheets().add("/gui/fxml/style/Module.css");

		topPane = new AnchorPane();
		topPane.relocate(10, 5);
		this.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
			// Making sure the pane is always the right size
			topPane.setPrefWidth(oldValue.getWidth() - 20.0d);
		});
		this.getChildren().add(topPane);

		moduleName = new Label();
		moduleName.setFont(new Font("Arial", 25));
		topPane.getChildren().add(moduleName);
		AnchorPane.setLeftAnchor(moduleName, 0.0d);

		closeButton = new Button();
		closeButton.getStyleClass().add("close-button");
		closeButton.setPrefSize(5, 5);
		topPane.getChildren().add(closeButton);
		AnchorPane.setRightAnchor(closeButton, 0.0d);
		closeButton.setOnMouseClicked(event -> {
			logger.fine("Module \""+module.getName()+"\" is asking to be closed.");
			workbench.onModuleCloseRequest(this);
			event.consume();
		});

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

		for(Node child : this.lookupAll("Plug")){
			if(child instanceof Plug){
				Plug plug = (Plug)child;
				plug.setWorkbench(workbench);
				plug.setGetPortCommand(() ->  module.getPort(plug.nameProperty().getValue()));
			}
		}
		topPane.toFront();
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
		moduleName.setText(module.getName());
	}
}
