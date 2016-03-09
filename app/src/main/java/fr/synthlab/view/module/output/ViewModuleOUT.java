package fr.synthlab.view.module.output;

import fr.synthlab.view.component.Knob;
import javafx.css.Styleable;
import javafx.event.EventTarget;
import javafx.fxml.Initializable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * view module out.
 */
public interface ViewModuleOUT
        extends EventTarget, Styleable, Serializable, Initializable {
    /**
     * @return knob for volume
     */
    Knob getPicker();

    /**
     * setter command on volume change.
     * @param newVolume execute on change
     */
    void setVolumeCommand(Runnable newVolume);

    /**
     * setter command on mute change.
     * @param mute execute on change
     */
    void setMuteCommand(Runnable mute);

    /**
     * @return if is mute
     */
    boolean isMute();

    /**
     * setter command on record change.
     * @param record execute on change
     */
    void setRecordCommand(Runnable record);

    /**
     * @return if is recording
     */
    boolean isRecording();

    /**
     * setter on record.
     * @param value to set
     */
    void setIsRecording(boolean value);

    /**
     * @return directory file for audio save
     */
    File getRecordingFile();

    @Override
    void initialize(
            URL location, ResourceBundle resources);

    /**
     * write in o for save workbench.
     * @param o where is save
     * @throws IOException if save can't open
     */
    void writeObject(ObjectOutputStream o)
            throws IOException;

    /**
     * reload object.
     * @param o where is reload
     * @throws IOException if save can't open
     * @throws ClassNotFoundException if a save class can't be found
     */
    void readObject(ObjectInputStream o)
            throws IOException, ClassNotFoundException;
}
