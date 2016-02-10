package fr.synthlab.view.module;

import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.OscilloscopeDrawing;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleOscilloscope extends ViewModule implements Initializable {
	private static final Logger logger = Logger.getLogger(ViewModuleOscilloscope.class.getName());

	@FXML
	OscilloscopeDrawing oscilloscopeDrawing;

	@FXML
	private Knob picker;

	private Runnable pickerCmd;
	private Workbench w;
	public ViewModuleOscilloscope(Workbench workbench) {
		super(workbench);
		this.loadFXML("/gui/fxml/module/ViewModuleOscilloscope.fxml");
		w=workbench;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		picker.valueProperty().addListener(event -> {
			pickerCmd.run();
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
