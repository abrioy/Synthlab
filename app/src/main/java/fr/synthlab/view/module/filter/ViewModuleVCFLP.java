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
 * view module VCFLP.
 */
public interface ViewModuleVCFLP
        extends EventTarget, Styleable, Serializable, Initializable {
    @Override
    void initialize(
            URL url, ResourceBundle resourceBundle);

    /**
     * setter on command execute on change frequency.
     * @param newChangeThresholdCommand to set
     */
    void setChangeThresholdCommand(
            Runnable newChangeThresholdCommand);

    /**
     * @return current frequency
     */
    double getThreshold();

    /**
     * setter on command execute on change resonance.
     * @param newChangeResonanceCommand to set
     */
    void setChangeResonanceCommand(
            Runnable newChangeResonanceCommand);

    /**
     * @return current resonance
     */
    double getResonance();

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
