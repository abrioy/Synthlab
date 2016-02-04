package fr.synthlab.view.module;

import fr.synthlab.view.controller.Workbench;

import java.util.logging.Logger;

public class ViewModuleVCO extends ViewModule {
	private static final Logger logger = Logger.getLogger(ViewModuleVCO.class.getName());

	public ViewModuleVCO(Workbench workbench) {
		super(workbench);
		this.loadFXML("/gui/fxml/module/ViewModuleVCO.fxml");
		this.setId("pane");
	}

}
