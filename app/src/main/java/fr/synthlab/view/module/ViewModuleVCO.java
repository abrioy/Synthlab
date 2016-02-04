package fr.synthlab.view.module;

import fr.synthlab.model.Command;
import fr.synthlab.model.module.vcoa.ShapeEnum;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.Plug;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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

	// Commands
	private Command changeShapeCommand;
	private Command changeFreqCommand;

	public ViewModuleVCO() {
		super();
		this.loadFXML("/gui/fxml/module/ViewModuleVCO.fxml");
		this.setId("pane");
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		freq.valueProperty().addListener(event -> {
			changeFreqCommand.execute();
		});

		freqLine.valueProperty().addListener(event -> {
			changeFreqCommand.execute();
		});

		picker.onMouseClickedProperty().addListener(event -> {
			changeShapeCommand.execute();
		});
	}

	public void setChangeShapeCommand(Command changeShape) {
		this.changeShapeCommand = changeShape;
	}


	public void setChangeFreqCommand(Command changeFreq) {
		this.changeFreqCommand = changeFreq;
	}


	public double getFreq() {
		return freq.getValue();
	}

	public double getFreqFin() {
		return freqLine.getValue();
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
