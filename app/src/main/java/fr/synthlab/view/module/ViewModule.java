package fr.synthlab.view.module;

import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Plug;
import fr.synthlab.model.module.Module;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.logging.Logger;

public abstract class ViewModule extends StackPane {
	private static final Logger logger = Logger.getLogger(ViewModule.class.getName());

	private Workbench workbench;
	private Module module;

	public ViewModule(Workbench workbench) {
	
		super();

		this.workbench = workbench;

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

		for(Node child : this.lookupAll("Plug")){
			if(child instanceof Plug){
				Plug plug = (Plug)child;
				plug.setWorkbench(workbench);
				plug.setGetPortCommand(() ->  module.getPort(plug.nameProperty().getValue()));
			}
		}
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
}
