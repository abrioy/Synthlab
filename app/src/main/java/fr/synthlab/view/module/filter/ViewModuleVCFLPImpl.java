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

/**
 * Implementation view module VCFLP.
 */
public class ViewModuleVCFLPImpl extends ViewModule implements ViewModuleVCFLP {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(ViewModuleVCFLPImpl.class.getName());
    /**
     * plug in.
     */
    @FXML
    private Plug in;
    /**
     * plug out.
     */
    @FXML
    private Plug out;
    /**
     * plug am.
     */
    @FXML
    private Plug am;

    /**
     * knob threshold.
     */
    @FXML
    private Knob threshold;

    /**
     * knob resonance.
     */
    @FXML
    private Knob resonance;

    /**
     * frequency label.
     */
    @FXML
    private Label frequencyLabel;

    /**
     * command execute on change frequency.
     */
    private Runnable changeThresholdCommand;
    /**
     * command execute on change resonance.
     */
    private Runnable changeResonanceCommand;

    /**
     * constructor.
     * @param workbench current workbench
     */
    public ViewModuleVCFLPImpl(final Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleVCFLP.fxml");
    }

    @Override
    public final void initialize(
            final URL url, final ResourceBundle resourceBundle) {
        threshold.valueProperty().addListener(event -> {
            updateThreshold();
        });

        resonance.valueProperty().addListener(event -> {
            updateResonance();
        });

        frequencyLabel.setText(((int) getThreshold()) + " Hz");
    }

    /**
     * execute update frequency.
     */
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

    /**
     * execute update resonance.
     */
    private void updateResonance() {
        changeResonanceCommand.run();
    }

    @Override
    public final void setChangeResonanceCommand(
            final Runnable newChangeResonanceCommand) {
        changeResonanceCommand = newChangeResonanceCommand;
        this.changeResonanceCommand.run();
    }

    @Override
    public final double getResonance() {
        return resonance.getValue();
    }

    @Override
    public final void writeObject(final ObjectOutputStream o)
            throws IOException {
        o.writeDouble(threshold.getValue());
        o.writeDouble(resonance.getValue());
    }

    @Override
    public final void readObject(final ObjectInputStream o)
            throws IOException, ClassNotFoundException {
        threshold.setValue(o.readDouble());
        resonance.setValue(o.readDouble());
    }
}
