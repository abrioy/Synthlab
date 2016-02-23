package fr.synthlab.view.module.output;

import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.MuteButton;
import fr.synthlab.view.component.RecordButton;
import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.module.ViewModule;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleOUT extends ViewModule implements Initializable{
    private static final Logger LOGGER = Logger.getLogger(ViewModuleOUT.class.getName());

    @FXML private Knob picker;
    @FXML private MuteButton muteButton;
    @FXML private RecordButton recordButton;
    @FXML private Button fileChooserButton;

    private Runnable volume;
    private Runnable muteCommand;
	private Runnable recordCommand;

	private BooleanProperty isMuted = new SimpleBooleanProperty();
    private BooleanProperty isRecording = new SimpleBooleanProperty();
    private File pickedDirectory;

    public ViewModuleOUT(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleOUT.fxml");
        this.setId("pane");
        muteButton.setPrefSize(40, 40);
        recordButton.setPrefSize(35, 35);
		fileChooserButton.setPrefSize(30, 30);
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
		return isMuted.getValue();
    }

    public void setRecordCommand(Runnable record) {
        this.recordCommand = record;
    }

    public boolean isRecording() {
        return isRecording.getValue();
    }

    public void setIsRecording(boolean value) {
        isRecording.setValue(value);
    }

    public File getRecordingFile() {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String date = formatter.format(new Date());
		String filename = "Synthlab_recording_"+date+".wav";

		File file;
		if(pickedDirectory == null){
			file = new File(filename);
		} else {
			file = new File(pickedDirectory.getPath() + File.separator + filename);
		}

		try {
			if (!file.createNewFile()) {
				file = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			file = null;
		}

		if(file != null) {
			return file;
		} else {
			LOGGER.warning("Unable to create a new file to record.");
			return null;
		}
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        picker.valueProperty().addListener(event -> {
            volume.run();
        });
        muteButton.setOnAction(event -> {
			isMuted.setValue(!isMuted.getValue());
		});

		isMuted.addListener((observable, oldValue, newValue) -> {
			muteButton.setToggle(newValue);
			muteCommand.run();
		});


		isRecording.addListener((observable, oldValue, newValue) -> {
			recordButton.setToggle(newValue);
			recordCommand.run();
		});

        recordButton.setOnAction(event -> {
			isRecording.setValue(!isRecording.getValue());
		});
		fileChooserButton.getStyleClass().add("file-button");
		fileChooserButton.setOnAction(event ->{
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("Output folder");

			if(pickedDirectory != null) {
				directoryChooser.setInitialDirectory(pickedDirectory.getParentFile());
			}

			File selectedDirectory = directoryChooser.showDialog(getScene().getWindow());

			if(selectedDirectory != null) {
				LOGGER.info("New recording output directory selected: \""+selectedDirectory.getPath()+"\".");
				pickedDirectory = selectedDirectory;
			}
		});

    }

	@Override
	public void writeObject(ObjectOutputStream o) throws IOException {
		o.writeDouble(picker.getValue());
		o.writeBoolean(isMuted.getValue());
	}

	@Override
	public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
		picker.setValue(o.readDouble());
		isMuted.setValue(o.readBoolean());
	}
}
