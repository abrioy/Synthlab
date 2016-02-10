package fr.synthlab.view.module;

import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Knob;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleEG extends ViewModule implements Initializable{
    private static final Logger logger = Logger.getLogger(ViewModuleEG.class.getName());

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


    public ViewModuleEG(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleEG.fxml");
        this.setId("pane");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    public double getAttack() { return attack.getValue(); }
    public double getDecay() { return decay.getValue(); }
    public double getSustain() { return sustain.getValue(); }
    public double getRelease() { return release.getValue(); }

    public void setChangeAttackCommand(Runnable command) {
        this.changeAttackCommand = command;
    }
    public void setChangeDecayCommand(Runnable command) {
        this.changeDecayCommand = command;
    }
    public void setChangeSustainCommand(Runnable command) {
        this.changeSustainCommand = command;
    }
    public void setChangeReleaseCommand(Runnable command) {
        this.changeReleaseCommand = command;
    }



}
