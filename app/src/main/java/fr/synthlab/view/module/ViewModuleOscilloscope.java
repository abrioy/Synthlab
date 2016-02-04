package fr.synthlab.view.module;

import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.view.component.OscilloscopeDrawing;
import javafx.fxml.FXML;

import java.util.logging.Logger;

public class ViewModuleOscilloscope extends ViewModule {
	private static final Logger logger = Logger.getLogger(ViewModuleOscilloscope.class.getName());

	@FXML
	OscilloscopeDrawing oscilloscopeDrawing;

	public ViewModuleOscilloscope() {
		super();
		this.loadFXML("/gui/fxml/module/ViewModuleOscilloscope.fxml");
		this.setId("pane");
	}

}
