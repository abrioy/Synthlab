package fr.synthlab.view.module.output;

import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.OscilloscopeDrawing;
import fr.synthlab.view.module.ViewModule;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleSCOP extends ViewModule implements Initializable {
	private static final Logger LOGGER = Logger.getLogger(ViewModuleSCOP.class.getName());

	@FXML
	OscilloscopeDrawing oscilloscopeDrawing;

	@FXML
	private Knob picker;

	private Runnable pickerCmd;
	private Workbench w;
	public ViewModuleSCOP(Workbench workbench) {
		super(workbench);
		this.loadFXML("/gui/fxml/module/ViewModuleOscilloscope.fxml");
		w=workbench;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		picker.valueProperty().addListener(event -> {
			pickerCmd.run();
		});

	}

	public void setPickerCommand(Runnable pickerCmd) {
		this.pickerCmd = pickerCmd;
		pickerCmd.run();
	}

	public int getScale() {
		return (int) picker.getValue();
	}

	public OscilloscopeDrawing getOscilloscopeDrawing() {
		return oscilloscopeDrawing;
	}

	@Override
	public void writeObject(ObjectOutputStream o) throws IOException {
		o.writeDouble(picker.getValue());
	}

	@Override
	public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
		picker.setValue(o.readDouble());
	}
}
