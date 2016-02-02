package fr.synthlab.view.module;

import javafx.fxml.FXML;
import org.apache.log4j.Logger;

public class ViewModuleVCO extends ViewModuleFrame {
	private static final Logger logger = Logger.getLogger(ViewModuleVCO.class);



	public ViewModuleVCO() {
		super();
		this.loadFXML("/gui/fxml/module/ViewModuleVCO.fxml");

	}

}
