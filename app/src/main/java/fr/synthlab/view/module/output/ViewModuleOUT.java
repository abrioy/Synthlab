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

public class ViewModuleOUT extends ViewModule implements Initializable {
    private static final Logger LOGGER
            = Logger.getLogger(ViewModuleOUT.class.getName());

    @FXML
    private Knob picker;
    @FXML
    private MuteButton muteButton;
    @FXML
    private RecordButton recordButton;
    @FXML
    private Button fileChooserButton;

    private Runnable volume;
    private Runnable muteCommand;
    private Runnable recordCommand;

    private BooleanProperty isMuted = new SimpleBooleanProperty();
    private BooleanProperty isRecording = new SimpleBooleanProperty();
    private File pickedDirectory;

    public ViewModuleOUT(final Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleOUT.fxml");
        this.setId("pane");
        muteButton.setPrefSize(40, 40);
        recordButton.setPrefSize(35, 35);
        fileChooserButton.setPrefSize(30, 30);
    }

    public final Knob getPicker() {
        return picker;
    }

    public final void setVolumeCommand(final Runnable newVolume) {
        volume = newVolume;

        // Init volume to the correct value
        volume.run();
    }

    public final void setMuteCommand(final Runnable mute) {
        this.muteCommand = mute;
        muteCommand.run();
    }

    public final boolean isMute() {
        return isMuted.getValue();
    }

    public final void setRecordCommand(final Runnable record) {
        this.recordCommand = record;
    }

    public final boolean isRecording() {
        return isRecording.getValue();
    }

    public final void setIsRecording(final boolean value) {
        isRecording.setValue(value);
    }

    public final File getRecordingFile() {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String date = formatter.format(new Date());
        String filename = "Synthlab_recording_" + date + ".wav";

        File file;
        if (pickedDirectory == null) {
            file = new File(filename);
        } else {
            file = new File(pickedDirectory.getPath()
                    + File.separator + filename);
        }

        try {
            if (!file.createNewFile()) {
                file = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            file = null;
        }

        if (file != null) {
            return file;
        } else {
            LOGGER.warning("Unable to create a new file to record.");
            return null;
        }
    }

    @Override
    public final void initialize(
            final URL location, final ResourceBundle resources) {
        picker.valueProperty().addListener(event -> {
            volume.run();
        });
        muteButton.setOnAction(event -> isMuted.setValue(!isMuted.getValue()));

        isMuted.addListener((observable, oldValue, newValue) -> {
            muteButton.setToggle(newValue);
            muteCommand.run();
        });

        isRecording.addListener((observable, oldValue, newValue) -> {
            recordButton.setToggle(newValue);
            recordCommand.run();
        });

        recordButton.setOnAction(event ->
                isRecording.setValue(!isRecording.getValue()));

        fileChooserButton.getStyleClass().add("file-button");
        fileChooserButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Output folder");

            if (pickedDirectory != null) {
                directoryChooser.setInitialDirectory(
                        pickedDirectory.getParentFile());
            }

            File selectedDirectory = directoryChooser.showDialog(
                    getScene().getWindow());

            if (selectedDirectory != null) {
                LOGGER.info("New recording output directory selected: \""
                        + selectedDirectory.getPath() + "\".");
                pickedDirectory = selectedDirectory;
            }
        });

    }

    @Override
    public final void writeObject(final ObjectOutputStream o)
            throws IOException {
        o.writeDouble(picker.getValue());
        o.writeBoolean(isMuted.getValue());
    }

    @Override
    public final void readObject(final ObjectInputStream o)
            throws IOException, ClassNotFoundException {
        picker.setValue(o.readDouble());
        isMuted.setValue(o.readBoolean());
    }
}
