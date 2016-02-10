package fr.synthlab.view.module;

import fr.synthlab.model.Command;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Knob;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleMixer extends ViewModule implements Initializable{
    private static final Logger logger = Logger.getLogger(ViewModuleMixer.class.getName());

    @FXML
    private Knob attenuator1;

    @FXML
    private Knob attenuator2;

    @FXML
    private Knob attenuator3;

    public double getAttenuator4() {
        return attenuator4.getValue();
    }

    public double getAttenuator1() {
        return attenuator1.getValue();
    }

    public double getAttenuator2() {
        return attenuator2.getValue();
    }

    public double getAttenuator3() {
        return attenuator3.getValue();
    }

    @FXML
    private Knob attenuator4;

    public void setAttenuator1Cmd(Command attenuator1Cmd) {
        this.attenuator1Cmd = attenuator1Cmd;
    }

    public void setAttenuator2Cmd(Command attenuator2Cmd) {
        this.attenuator2Cmd = attenuator2Cmd;
    }

    public void setAttenuator3Cmd(Command attenuator3Cmd) {
        this.attenuator3Cmd = attenuator3Cmd;
    }

    public void setAttenuator4Cmd(Command attenuator4Cmd) {
        this.attenuator4Cmd = attenuator4Cmd;
    }

    private Command attenuator1Cmd;
    private Command attenuator2Cmd;
    private Command attenuator3Cmd;
    private Command attenuator4Cmd;

    public ViewModuleMixer(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleMixer.fxml");
        this.setId("pane");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attenuator1.valueProperty().addListener(event -> {
            attenuator1Cmd.execute();
        });
        attenuator2.valueProperty().addListener(event -> {
            attenuator2Cmd.execute();
        });
        attenuator3.valueProperty().addListener(event -> {
            attenuator3Cmd.execute();
        });
        attenuator4.valueProperty().addListener(event -> {
            attenuator4Cmd.execute();
        });
    }
}
