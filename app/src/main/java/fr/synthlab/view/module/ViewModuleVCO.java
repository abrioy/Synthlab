package fr.synthlab.view.module;

import java.util.logging.Logger;

public class ViewModuleVCO extends ViewModule {
	private static final Logger logger = Logger.getLogger(ViewModuleVCO.class.getName());

	public ViewModuleVCO() {
		super();
		this.loadFXML("/gui/fxml/module/ViewModuleVCO.fxml");
		this.setId("pane");
	}

}
