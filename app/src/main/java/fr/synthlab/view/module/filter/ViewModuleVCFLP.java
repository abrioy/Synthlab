package fr.synthlab.view.module.filter;

import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.Plug;
import fr.synthlab.view.module.ViewModule;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleVCFLP extends ViewModule implements Initializable {
	private static final Logger logger = Logger.getLogger(ViewModuleVCFLP.class.getName());

	@FXML
	private Plug in;
	@FXML
	private Plug out;
	@FXML
    private Plug am;

    @FXML
    private Knob threshold;

	@FXML
	private Knob resonance;

	@FXML
	private Label frequencyLabel;

	private Runnable changeThresholdCommand;
	private Runnable changeResonanceCommand;

    public ViewModuleVCFLP(Workbench workbench) {
		super(workbench);
		this.loadFXML("/gui/fxml/module/ViewModuleVCFLP.fxml");
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		threshold.valueProperty().addListener(event -> {
            updateThreshold();
        });

		resonance.valueProperty().addListener(event -> {
			updateResonance();
		});

		frequencyLabel.setText(((int)getThreshold())+" Hz");
    }

    private void updateThreshold() {
		changeThresholdCommand.run();
		frequencyLabel.setText(((int)getThreshold())+" Hz");
    }

	public void setChangeThresholdCommand(Runnable changeThresholdCommand) {
		this.changeThresholdCommand = changeThresholdCommand;

		changeThresholdCommand.run();
	}

    public double getThreshold() {
        return threshold.getValue();
    }


	private void updateResonance() {
		changeResonanceCommand.run();
	}

	public void setChangeResonanceCommand(Runnable changeResonanceCommand) {
		this.changeResonanceCommand = changeResonanceCommand;

		changeResonanceCommand.run();
	}

	public double getResonance() {
		return resonance.getValue();
	}

	@Override
	public void writeObject(ObjectOutputStream o) throws IOException {
		o.writeDouble(threshold.getValue());
		o.writeDouble(resonance.getValue());
	}

	@Override
	public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
		threshold.setValue(o.readDouble());
		resonance.setValue(o.readDouble());
	}
}
