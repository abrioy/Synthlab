package fr.synthlab.view.module;

import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.MuteButton;
import fr.synthlab.view.component.RecordButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleOUT extends ViewModule implements Initializable{
    private static final Logger logger = Logger.getLogger(ViewModuleOUT.class.getName());

    @FXML
    private Knob picker;

    @FXML
    private MuteButton muteButton;

    @FXML
    private RecordButton recordButton;

    private Runnable volume;

    private Runnable muteCommand;

    private Runnable recordCommand;

    private boolean mute;

    private boolean record;

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
        return mute;
    }

    public void setRecordCommand(Runnable record) {
        this.recordCommand = record;
        recordCommand.run();
    }

    public boolean isRecording() {
        return record;
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
            mute = !mute;
            muteButton.setToggle(mute);
            muteCommand.run();
        });
        recordButton.setOnAction(event -> {
            if (!record) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save file");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WAV files (.wav)", "*.WAV"));
                pickedFile = fileChooser.showSaveDialog(getScene().getWindow());

                if (pickedFile != null) {
                    if (!pickedFile.getAbsolutePath().endsWith(".wav"))
                        pickedFile = new File(pickedFile.getAbsolutePath() + ".wav");

                    record = !record;
                    recordButton.setToggle(record);
                    recordCommand.run();
                }
            } else {
                record = !record;
                recordButton.setToggle(record);
                recordCommand.run();
            }
        });
    }
}
