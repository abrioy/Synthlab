package fr.synthlab.view.module.filter;


import fr.synthlab.view.controller.workbench.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.Plug;
import fr.synthlab.view.module.ViewModule;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleVCFHPImpl extends ViewModule implements ViewModuleVCFHP {
    private static final Logger LOGGER
            = Logger.getLogger(ViewModuleVCFLPImpl.class.getName());

    @FXML
    private Plug in;
    @FXML
    private Plug out;
    @FXML
    private Plug am;

    @FXML
    private Knob threshold;

    @FXML
    private Label frequencyLabel;

    private Runnable changeThresholdCommand;

    public ViewModuleVCFHPImpl(final Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleVCFHP.fxml");
    }

    @Override
    public final void initialize(
			final URL url, final ResourceBundle resourceBundle) {
        threshold.valueProperty().addListener(event -> {
            updateThreshold();
        });
        frequencyLabel.setText(((int) getThreshold()) + " Hz");
    }

    private void updateThreshold() {
        changeThresholdCommand.run();
        frequencyLabel.setText(((int) getThreshold()) + " Hz");
    }

    @Override
	public final void setChangeThresholdCommand(
			final Runnable newChangeThresholdCommand) {
        changeThresholdCommand = newChangeThresholdCommand;
        changeThresholdCommand.run();
    }

    @Override
	public final double getThreshold() {
        return threshold.getValue();
    }

    @Override
    public final void writeObject(final ObjectOutputStream o)
            throws IOException {
        o.writeDouble(threshold.getValue());
    }

    @Override
    public final void readObject(final ObjectInputStream o)
            throws IOException, ClassNotFoundException {
        threshold.setValue(o.readDouble());
    }
}
