package fr.synthlab.view.module.filter;

import fr.synthlab.view.controller.workbench.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.module.ViewModule;
import javafx.fxml.FXML;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Implementation of view module EG.
 */
public class ViewModuleEGImpl extends ViewModule implements ViewModuleEG {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(ViewModuleEGImpl.class.getName());
    /**
     * knob on attack.
     */
    @FXML
    private Knob attack;
    /**
     * knob on decay.
     */
    @FXML
    private Knob decay;
    /**
     * knob on sustain.
     */
    @FXML
    private Knob sustain;
    /**
     * knob on release.
     */
    @FXML
    private Knob release;

    /**
     * setter on command execute on change attack.
     */
    private Runnable changeAttackCommand;
    /**
     * setter on command execute on change decay.
     */
    private Runnable changeDecayCommand;
    /**
     * setter on command execute on change sustain.
     */
    private Runnable changeSustainCommand;
    /**
     * setter on command execute on change release.
     */
    private Runnable changeReleaseCommand;

    /**
     * constructor.
     * @param workbench current workbench
     */
    public ViewModuleEGImpl(final Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleEG.fxml");
        this.setId("pane");
    }

    @Override
    public final void initialize(
            final URL location, final ResourceBundle resources) {
        attack.valueProperty().addListener(event -> {
            changeAttackCommand.run();
        });

        decay.valueProperty().addListener(event -> {
            changeDecayCommand.run();
        });

        sustain.valueProperty().addListener(event -> {
            changeSustainCommand.run();
        });

        release.valueProperty().addListener(event -> {
            changeReleaseCommand.run();
        });
    }

    @Override
    public final double getAttack() {
        return attack.getValue();
    }

    @Override
    public final double getDecay() {
        return decay.getValue();
    }

    @Override
    public final double getSustain() {
        return sustain.getValue();
    }

    @Override
    public final double getRelease() {
        return release.getValue();
    }

    @Override
    public final void setChangeAttackCommand(final Runnable command) {
        this.changeAttackCommand = command;
        this.changeAttackCommand.run();
    }

    @Override
    public final void setChangeDecayCommand(final Runnable command) {
        this.changeDecayCommand = command;
        this.changeDecayCommand.run();
    }

    @Override
    public final void setChangeSustainCommand(final Runnable command) {
        this.changeSustainCommand = command;
        this.changeSustainCommand.run();
    }

    @Override
    public final void setChangeReleaseCommand(final Runnable command) {
        this.changeReleaseCommand = command;
        this.changeReleaseCommand.run();
    }

    @Override
    public final void writeObject(final ObjectOutputStream o)
            throws IOException {
        o.writeDouble(attack.getValue());
        o.writeDouble(decay.getValue());
        o.writeDouble(sustain.getValue());
        o.writeDouble(release.getValue());
    }

    @Override
    public final void readObject(final ObjectInputStream o)
            throws IOException, ClassNotFoundException {
        attack.setValue(o.readDouble());
        decay.setValue(o.readDouble());
        sustain.setValue(o.readDouble());
        release.setValue(o.readDouble());
    }
}
