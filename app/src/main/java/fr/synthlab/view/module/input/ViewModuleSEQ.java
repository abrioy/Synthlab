package fr.synthlab.view.module.input;

import fr.synthlab.view.component.Knob;
import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.component.ResetButton;
import fr.synthlab.view.module.ViewModule;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class ViewModuleSEQ extends ViewModule implements Initializable, Observer{
    private static final Logger logger = Logger.getLogger(ViewModuleSEQ.class.getName());

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


    public ViewModuleSEQ(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleSEQ.fxml");
        this.setId("pane");
        //resetButton.setPrefSize(30,30);
    }

    public void setResetCommand(Runnable reset) {
        this.resetCommand = reset;
    }

    public void setChangeStep1Command(Runnable command) {
        this.step1Command = command;
    }
    public void setChangeStep2Command(Runnable command) {
        this.step2Command = command;
    }
    public void setChangeStep3Command(Runnable command) {
        this.step3Command = command;
    }
    public void setChangeStep4Command(Runnable command) {
        this.step4Command = command;
    }
    public void setChangeStep5Command(Runnable command) {
        this.step5Command = command;
    }
    public void setChangeStep6Command(Runnable command) {
        this.step6Command = command;
    }
    public void setChangeStep7Command(Runnable command) {
        this.step7Command = command;
    }
    public void setChangeStep8Command(Runnable command) {
        this.step8Command = command;
    }

    public double getStepValue(int step) {
        return stepPickers.get(step).getValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stepPickers = new ArrayList<Knob>();

        stepPickers.add(step1Picker);
        stepPickers.add(step2Picker);
        stepPickers.add(step3Picker);
        stepPickers.add(step4Picker);
        stepPickers.add(step5Picker);
        stepPickers.add(step6Picker);
        stepPickers.add(step7Picker);
        stepPickers.add(step8Picker);

        resetButton.setOnAction(event -> {
            resetCommand.run();
        });

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
    public void writeObject(ObjectOutputStream o) throws IOException {
		//TODO
    }

    @Override
    public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
		//TODO
    }

    @Override
    public void update(Observable o, Object arg) {
        stepLabel.setText(((int) arg + 1) + "");
        // TODO Increase the step somewhere
    }
}
