package fr.synthlab.view.module;

import fr.synthlab.model.Command;
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

    private Command changeAttackCommand;
    private Command changeDecayCommand;
    private Command changeSustainCommand;
    private Command changeReleaseCommand;


    public ViewModuleEG(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleEG.fxml");
        this.setId("pane");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attack.valueProperty().addListener(event -> {
            changeAttackCommand.execute();
        });

        decay.valueProperty().addListener(event -> {
            changeDecayCommand.execute();
        });

        sustain.valueProperty().addListener(event -> {
            changeSustainCommand.execute();
        });

        release.valueProperty().addListener(event -> {
            changeReleaseCommand.execute();
        });
    }

    public double getAttack() { return attack.getValue(); }
    public double getDecay() { return decay.getValue(); }
    public double getSustain() { return sustain.getValue(); }
    public double getRelease() { return release.getValue(); }

    public void setChangeAttackCommand(Command command) {
        this.changeAttackCommand = command;
    }
    public void setChangeDecayCommand(Command command) {
        this.changeDecayCommand = command;
    }
    public void setChangeSustainCommand(Command command) {
        this.changeSustainCommand = command;
    }
    public void setChangeReleaseCommand(Command command) {
        this.changeReleaseCommand = command;
    }



}
