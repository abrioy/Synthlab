package fr.synthlab.view.module;

import java.util.logging.Logger;

public class ViewModuleOUT extends ViewModule {
	private static final Logger logger = Logger.getLogger(ViewModuleOUT.class.getName());

	public ViewModuleOUT() {
		super();
		this.loadFXML("/gui/fxml/module/ViewModuleOUT.fxml");

	}

}
