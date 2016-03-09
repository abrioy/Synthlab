package fr.synthlab.view.module.input;

import fr.synthlab.model.module.keyboard.NoteKEYB;
import javafx.css.Styleable;
import javafx.event.EventTarget;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * View module Keyboard.
 */
public interface ViewModuleKEYB
        extends EventTarget, Styleable, Serializable, Initializable {
    @Override
    void initialize(
            URL location, ResourceBundle resources);

    /**
     * @return current octave
     */
    int getOctave();

    /**
     * setter on command execute on key pressed.
     * @param command to set
     */
    void setKeyPressedCommand(Runnable command);
    /**
     * setter on command execute on key released.
     * @param command to set
     */
    void setKeyReleasedCommand(Runnable command);
    /**
     * setter on command execute on octave change.
     * @param command to set
     */
    void setOctaveChangeCommand(Runnable command);

    /**
     * @return last note pressed
     */
    NoteKEYB getNotePressed();

    /**
     * @return last key released
     */
    NoteKEYB getLastKeyReleased();

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
