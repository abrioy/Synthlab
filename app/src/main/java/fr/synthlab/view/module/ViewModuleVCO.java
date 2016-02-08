package fr.synthlab.view.module;

import fr.synthlab.model.Command;
import fr.synthlab.model.module.vcoa.ShapeEnum;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.Plug;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleVCO extends ViewModule implements Initializable {
	private static final Logger logger = Logger.getLogger(ViewModuleVCO.class.getName());

	@FXML
	private Knob freq;
	@FXML
	private Knob freqLine;
	@FXML
	private Knob picker;
	@FXML
	private Plug in;
	@FXML
	private Plug out;
	@FXML
	private Label frequencyLabel;

	// Commands
	private Command changeShapeCommand;
	private Command changeFreqCommand;

	public ViewModuleVCO(Workbench workbench) {
		super(workbench);
		this.loadFXML("/gui/fxml/module/ViewModuleVCO.fxml");
		this.setId("pane");
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		freq.valueProperty().addListener(event -> {
			updateFrequency();
		});

		freqLine.valueProperty().addListener(event -> {
			updateFrequency();
		});

		picker.valueProperty().addListener(event -> {
			changeShapeCommand.execute();
		});

		frequencyLabel.setText(((int)getFreq())+" Hz");
	}

	private void updateFrequency() {
		changeFreqCommand.execute();
		frequencyLabel.setText(((int)getFreq())+" Hz");
	}

	public void setChangeShapeCommand(Command changeShape) {
		this.changeShapeCommand = changeShape;

		changeShapeCommand.execute();
	}


	public void setChangeFreqCommand(Command changeFreq) {
		this.changeFreqCommand = changeFreq;

		changeFreqCommand.execute();
	}


	public double getFreq() {
		return freq.getValue() + freqLine.getValue();
	}


	public ShapeEnum getSelectedShape() {
		switch((int)picker.getValue()) {
			case 0:
				return ShapeEnum.TRIANGLE;
			case 1:
				return ShapeEnum.SQUARE;
			default:
				return ShapeEnum.SAWTOOTH;
		}
	}
}
