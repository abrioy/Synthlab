package fr.synthlab.view.module.filter;

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
public interface ViewModuleVCFLP extends EventTarget, Styleable, Serializable, Initializable {
    @Override
    void initialize(
            URL url, ResourceBundle resourceBundle);

    void setChangeThresholdCommand(
            Runnable newChangeThresholdCommand);

    double getThreshold();

    void setChangeResonanceCommand(
            Runnable newChangeResonanceCommand);

    double getResonance();

    void writeObject(ObjectOutputStream o)
                                    throws IOException;

    void readObject(ObjectInputStream o)
                                            throws IOException, ClassNotFoundException;
}
