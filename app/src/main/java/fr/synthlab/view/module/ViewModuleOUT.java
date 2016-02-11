package fr.synthlab.view.module;

import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.MuteButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleOUT extends ViewModule implements Initializable{
    private static final Logger logger = Logger.getLogger(ViewModuleOUT.class.getName());

    @FXML
    private Knob picker;

    @FXML
    private MuteButton muteButton;

    private Runnable volume;

    private Runnable muteCommand;

    private BooleanProperty mute = new SimpleBooleanProperty();

    public ViewModuleOUT(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleOUT.fxml");
        this.setId("pane");
        muteButton.setPrefSize(30,30);
    }

    public Knob getPicker() {
        return picker;
    }

    public void setVolumeCommand(Runnable volume) {
        this.volume = volume;

        // Init volume to the correct value
        volume.run();
    }

    public void setMuteCommand(Runnable mute) {
        this.muteCommand = mute;
    }

    public boolean isMute() {
        return mute.getValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        picker.valueProperty().addListener(event -> {
            volume.run();
        });
        muteButton.setOnAction(event -> {
            mute.setValue(!mute.getValue());
            muteCommand.run();
        });
		mute.addListener((observable, oldValue, newValue) -> {
			muteButton.setToggle(newValue);
		});
    }

	@Override
	public void writeObject(ObjectOutputStream o) throws IOException {
		o.writeDouble(picker.getValue());
		o.writeBoolean(mute.getValue());
	}

	@Override
	public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
		picker.setValue(o.readDouble());
		mute.setValue(o.readBoolean());
	}
}
