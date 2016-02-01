package fr.synthlab.view.module;

import javafx.scene.layout.StackPane;
import org.apache.log4j.Logger;

public abstract class ViewModuleFrame extends StackPane {
	private static final Logger logger = Logger.getLogger(ViewModuleFrame.class);

	public ViewModuleFrame() {
		super();

		this.getStyleClass().add("view-module");
	}
}
