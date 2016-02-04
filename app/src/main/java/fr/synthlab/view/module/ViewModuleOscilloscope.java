package fr.synthlab.view.module;

import fr.synthlab.view.component.OscilloscopeDrawing;
import fr.synthlab.view.controller.Workbench;
import javafx.fxml.FXML;

import java.util.logging.Logger;

public class ViewModuleOscilloscope extends ViewModule {
	private static final Logger logger = Logger.getLogger(ViewModuleOscilloscope.class.getName());

	@FXML
	OscilloscopeDrawing oscilloscopeDrawing;

	public ViewModuleOscilloscope(Workbench workbench) {
		super(workbench);
		this.loadFXML("/gui/fxml/module/ViewModuleOscilloscope.fxml");
	}

}
