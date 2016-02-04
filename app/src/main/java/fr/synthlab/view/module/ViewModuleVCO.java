package fr.synthlab.view.module;

import fr.synthlab.model.Command;

import java.util.logging.Logger;

public class ViewModuleVCO extends ViewModule {
	private static final Logger logger = Logger.getLogger(ViewModuleVCO.class.getName());

	// Commands
	private Command changeShape;
	private Command changeFreq;

	private double freq;
	private double freqFin;



	public ViewModuleVCO() {
		super();
		this.loadFXML("/gui/fxml/module/ViewModuleVCO.fxml");
		this.setId("pane");
	}


	public void setChangeShape(Command changeShape) {
		this.changeShape = changeShape;
	}


	public void setChangeFreq(Command changeFreq) {
		this.changeFreq = changeFreq;
	}


	public double getFreq() {
		return freq;
	}


	public double getFreqFin() {
		return freqFin;
	}

}
