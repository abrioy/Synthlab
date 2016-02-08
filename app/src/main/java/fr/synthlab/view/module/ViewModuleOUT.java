package fr.synthlab.view.module;

import fr.synthlab.model.Command;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Knob;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleOUT extends ViewModule implements Initializable{
    private static final Logger logger = Logger.getLogger(ViewModuleOUT.class.getName());

    @FXML
    private Knob picker;

    @FXML
    private Button muteButton;

    private Command volume;

    private Command muteCommand;

    private boolean mute;

    public Knob getPicker() {
        return picker;
    }

    public ViewModuleOUT(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleOUT.fxml");
        this.setId("pane");
        //todo add listener on mute
    }

    public void setVolumeCommand(Command volume) {
        this.volume = volume;
    }

    public void setMuteCommand(Command mute) {
        this.muteCommand = mute;
    }

    public boolean isMute() {
        return mute;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        picker.valueProperty().addListener(event -> {
            volume.execute();
        });
        muteButton.setOnAction(event -> {
            mute = !mute;
            muteCommand.execute();
        });
    }
}
