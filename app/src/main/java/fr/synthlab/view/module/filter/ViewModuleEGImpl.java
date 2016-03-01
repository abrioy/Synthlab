package fr.synthlab.view.module.filter;

import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.module.ViewModule;
import javafx.fxml.FXML;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleEGImpl extends ViewModule implements ViewModuleEG {
    private static final Logger LOGGER
            = Logger.getLogger(ViewModuleEGImpl.class.getName());

    @FXML
    private Knob attack;
    @FXML
    private Knob decay;
    @FXML
    private Knob sustain;
    @FXML
    private Knob release;

    private Runnable changeAttackCommand;
    private Runnable changeDecayCommand;
    private Runnable changeSustainCommand;
    private Runnable changeReleaseCommand;


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
