package fr.synthlab.view.module;

import fr.synthlab.model.Command;
import fr.synthlab.model.module.vcoa.ShapeEnum;

import java.util.logging.Logger;

public class ViewModuleVCO extends ViewModule {
	private static final Logger logger = Logger.getLogger(ViewModuleVCO.class.getName());

	// Commands
	private Command changeShapeCommand;
	private Command changeFreqCommand;

	private double freq;
	private double freqFin;
	private ShapeEnum shape;



	public ViewModuleVCO() {
		super();
		this.loadFXML("/gui/fxml/module/ViewModuleVCO.fxml");
		this.setId("pane");
	}


	public void setChangeShapeCommand(Command changeShape) {
		this.changeShapeCommand = changeShape;
	}


	public void setChangeFreqCommand(Command changeFreq) {
		this.changeFreqCommand = changeFreq;
	}


	public double getFreq() {
		return freq;
	}


	public double getFreqFin() {
		return freqFin;
	}

	public ShapeEnum getSelectedShape() {
		return shape;
	}

	public void setShape(ShapeEnum shape) {
		this.shape = shape;
	}
}
