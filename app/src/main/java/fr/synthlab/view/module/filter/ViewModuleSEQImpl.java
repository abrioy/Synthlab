package fr.synthlab.view.module.filter;

import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.ResetButton;
import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.module.ViewModule;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Logger;

public class ViewModuleSEQImpl extends ViewModule
        implements ViewModuleSEQ {
    private static final Logger LOGGER
            = Logger.getLogger(ViewModuleSEQImpl.class.getName());

    @FXML
    private ResetButton resetButton;

    @FXML
    private Knob step1Picker;
    @FXML
    private Knob step2Picker;
    @FXML
    private Knob step3Picker;
    @FXML
    private Knob step4Picker;
    @FXML
    private Knob step5Picker;
    @FXML
    private Knob step6Picker;
    @FXML
    private Knob step7Picker;
    @FXML
    private Knob step8Picker;

    @FXML
    private Label stepLabel;

    private List<Knob> stepPickers;

    private Runnable resetCommand;
    private Runnable step1Command;
    private Runnable step2Command;
    private Runnable step3Command;
    private Runnable step4Command;
    private Runnable step5Command;
    private Runnable step6Command;
    private Runnable step7Command;
    private Runnable step8Command;


    public ViewModuleSEQImpl(final Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleSEQ.fxml");
        this.setId("pane");
        //resetButton.setPrefSize(30,30);
    }

    @Override
	public final void setResetCommand(final Runnable reset) {
        this.resetCommand = reset;
    }

    @Override
	public final void setChangeStep1Command(final Runnable command) {
        this.step1Command = command;
    }

    @Override
	public final void setChangeStep2Command(final Runnable command) {
        this.step2Command = command;
    }

    @Override
	public final void setChangeStep3Command(final Runnable command) {
        this.step3Command = command;
    }

    @Override
	public final void setChangeStep4Command(final Runnable command) {
        this.step4Command = command;
    }

    @Override
	public final void setChangeStep5Command(final Runnable command) {
        this.step5Command = command;
    }

    @Override
	public final void setChangeStep6Command(final Runnable command) {
        this.step6Command = command;
    }

    @Override
	public final void setChangeStep7Command(final Runnable command) {
        this.step7Command = command;
    }

    @Override
	public final void setChangeStep8Command(final Runnable command) {
        this.step8Command = command;
    }

    @Override
	public final double getStepValue(final int step) {
        return stepPickers.get(step).getValue();
    }

    @Override
    public final void initialize(
			final URL location, final ResourceBundle resources) {
        stepPickers = new ArrayList<>();

        stepPickers.add(step1Picker);
        stepPickers.add(step2Picker);
        stepPickers.add(step3Picker);
        stepPickers.add(step4Picker);
        stepPickers.add(step5Picker);
        stepPickers.add(step6Picker);
        stepPickers.add(step7Picker);
        stepPickers.add(step8Picker);

        resetButton.setOnAction(event -> resetCommand.run());

        step1Picker.valueProperty().addListener(event -> {
            step1Command.run();
        });

        step2Picker.valueProperty().addListener(event -> {
            step2Command.run();
        });

        step3Picker.valueProperty().addListener(event -> {
            step3Command.run();
        });

        step4Picker.valueProperty().addListener(event -> {
            step4Command.run();
        });

        step5Picker.valueProperty().addListener(event -> {
            step5Command.run();
        });

        step6Picker.valueProperty().addListener(event -> {
            step6Command.run();
        });

        step7Picker.valueProperty().addListener(event -> {
            step7Command.run();
        });

        step8Picker.valueProperty().addListener(event -> {
            step8Command.run();
        });
    }

    @Override
    public final void writeObject(final ObjectOutputStream o)
            throws IOException {
        for (Knob a : stepPickers) {
            o.writeDouble(a.getValue());
        }
    }

    @Override
    public final void readObject(final ObjectInputStream o)
            throws IOException, ClassNotFoundException {
        for (Knob a : stepPickers) {
            a.setValue(o.readDouble());
        }
    }

    @Override
    public final void update(final Observable o, final Object arg) {
        Platform.runLater(() -> stepLabel.setText(arg.toString()));
    }
}
