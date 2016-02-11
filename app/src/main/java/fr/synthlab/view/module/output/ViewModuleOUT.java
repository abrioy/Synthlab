package fr.synthlab.view.module.output;

import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.MuteButton;
import fr.synthlab.view.module.ViewModule;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import fr.synthlab.view.component.RecordButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleOUT extends ViewModule implements Initializable{
    private static final Logger logger = Logger.getLogger(ViewModuleOUT.class.getName());

    @FXML private Knob picker;
    @FXML private MuteButton muteButton;
    @FXML private RecordButton recordButton;

    private Runnable volume;
    private Runnable muteCommand;
	private Runnable recordCommand;

	private BooleanProperty mute = new SimpleBooleanProperty();
    private boolean isRecording;
    private File pickedFile;

    public ViewModuleOUT(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleOUT.fxml");
        this.setId("pane");
        muteButton.setPrefSize(30,30);
        recordButton.setPrefSize(30,30);
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
        muteCommand.run();
    }

    public boolean isMute() {
		return mute.getValue();
    }

    public void setRecordCommand(Runnable record) {
        this.recordCommand = record;
        recordCommand.run();
    }

    public boolean isRecording() {
        return isRecording;
    }

    public File getPickedFile() {
        return pickedFile;
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


        recordButton.setOnAction(event -> {
            if (!isRecording) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save file");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WAV files (.wav)", "*.WAV"));
                pickedFile = fileChooser.showSaveDialog(getScene().getWindow());

                if (pickedFile != null) {
                    if (!pickedFile.getAbsolutePath().endsWith(".wav"))
                        pickedFile = new File(pickedFile.getAbsolutePath() + ".wav");

                    isRecording = !isRecording;
                    recordButton.setToggle(isRecording);
                    recordCommand.run();
                }
            } else {
                isRecording = !isRecording;
                recordButton.setToggle(isRecording);
                recordCommand.run();
            }
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
