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
 * view module VCA.
 */
public interface ViewModuleVCA
        extends EventTarget, Styleable, Serializable, Initializable {
    @Override
    void initialize(
            URL url, ResourceBundle resourceBundle);

    /**
     * setter on command execute on change amplification.
     * @param newChangeAmpliCommand to set
     */
    void setChangeAmpliCommand(
            Runnable newChangeAmpliCommand);

    /**
     * @return current amplification
     */
    double getAmpli();
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
