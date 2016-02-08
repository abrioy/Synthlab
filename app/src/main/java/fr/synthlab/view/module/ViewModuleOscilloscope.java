package fr.synthlab.view.module;

import fr.synthlab.model.Command;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.OscilloscopeDrawing;
import fr.synthlab.view.viewModuleFactory.ViewModuleFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleOscilloscope extends ViewModule implements Initializable {
	private static final Logger logger = Logger.getLogger(ViewModuleOscilloscope.class.getName());

	@FXML
	OscilloscopeDrawing oscilloscopeDrawing;

	@FXML
	private Knob picker;

	private Command pickerCmd;

	public ViewModuleOscilloscope(Workbench workbench) {
		super(workbench);
		this.loadFXML("/gui/fxml/module/ViewModuleOscilloscope.fxml");
		w=workbench;
	}
	private Workbench w;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		picker.valueProperty().addListener(event -> {
			pickerCmd.execute();
		});

		/*

		this.setOnMouseClicked(event -> {
			logger.info("CLICKED");
			ViewModule vco = ViewModuleFactory.createViewModule(ModuleEnum.VCOA, w);

			vco.getModule().getPort("out").connect(this.getModule().getPort("in"));

			w.addModule(vco);
		});

		*/

	}

	public void setPickerCmd(Command pickerCmd) {
		this.pickerCmd = pickerCmd;
	}

	public int getScale() {
		return (int) picker.getValue();
	}

	public OscilloscopeDrawing getOscilloscopeDrawing() {
		return oscilloscopeDrawing;
	}
}
