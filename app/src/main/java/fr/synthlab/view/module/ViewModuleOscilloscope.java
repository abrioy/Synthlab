package fr.synthlab.view.module;

import fr.synthlab.model.Command;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.OscilloscopeDrawing;
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

	public ViewModuleOscilloscope() {
		super();
		this.loadFXML("/gui/fxml/module/ViewModuleOscilloscope.fxml");
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		picker.valueProperty().addListener(event -> {
			pickerCmd.execute();
		});

	}

	public void setPickerCmd(Command pickerCmd) {
		this.pickerCmd = pickerCmd;
	}

	public int getScale() {
		return (int) picker.getValue();
	}
}
