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
 * Created by miow on 3/1/16.
 */
public interface ViewModuleKEYB extends EventTarget, Styleable, Serializable, Initializable {
    @Override
    void initialize(
            URL location, ResourceBundle resources);

    int getOctave();

    void setKeyPressedCommand(Runnable command);

    void setKeyReleasedCommand(Runnable command);

    void setOctaveChangeCommand(Runnable command);

    NoteKEYB getNotePressed();

    NoteKEYB getLastKeyReleased();

    void writeObject(ObjectOutputStream o)
                    throws IOException;

    void readObject(ObjectInputStream o)
                            throws IOException, ClassNotFoundException;
}
