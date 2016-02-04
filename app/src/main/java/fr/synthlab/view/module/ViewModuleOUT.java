package fr.synthlab.view.module;

import fr.synthlab.model.Command;
import fr.synthlab.view.component.Knob;
import javafx.fxml.FXML;

import java.util.logging.Logger;

public class ViewModuleOUT extends ViewModule {
    private static final Logger logger = Logger.getLogger(ViewModuleOUT.class.getName());

    @FXML Knob picker;

    private Command volume;

    private Command muteCommand;

    private boolean mute;

    public Knob getPicker() {
        return picker;
    }

    public ViewModuleOUT() {
        super();
        this.loadFXML("/gui/fxml/module/ViewModuleOUT.fxml");

        picker.valueProperty().addListener((arg0, arg1, arg2) -> {
            volume.execute();
        });
        //todo add listener on mute
    }

    public void setVolume(Command volume) {
        this.volume = volume;
    }

    public void setMute(Command mute) {
        this.muteCommand = mute;
    }

    public boolean isMute() {
        return mute;
    }
}
