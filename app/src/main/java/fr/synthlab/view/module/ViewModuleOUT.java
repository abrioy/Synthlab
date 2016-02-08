package fr.synthlab.view.module;

import fr.synthlab.model.Command;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Knob;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

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

    public ViewModuleOUT(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleOUT.fxml");
        this.setId("pane");
        //todo add listener on mute
    }

    public Knob getPicker() {
        return picker;
    }

    public void setVolume(Command volume) {
        this.volume = volume;

        // Init volume to the correct value
        volume.execute();
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(Command mute) {
        this.muteCommand = mute;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        picker.valueProperty().addListener(event -> {
            volume.execute();
        });
        muteButton.addEventHandler(MouseEvent.MOUSE_CLICKED,e -> {
            mute = !mute;
            muteCommand.execute();
        });
    }
}
