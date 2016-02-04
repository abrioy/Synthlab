package fr.synthlab.view.module;

import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.Plug;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

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

	public ViewModuleVCO() {
		super();
		this.loadFXML("/gui/fxml/module/ViewModuleVCO.fxml");
		this.setId("pane");
	}

	private void OnMouseDragOverFreq(MouseEvent event) {
		System.out.println(event.getX());
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		//freq.setOnMouseMoved(event -> OnMouseDragOverFreq(event));
	}
}
