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
 * view module EG.
 */
public interface ViewModuleEG
        extends EventTarget, Styleable, Serializable, Initializable {
    @Override
    void initialize(
            URL location, ResourceBundle resources);

    /**
     * @return attack
     */
    double getAttack();

    /**
     * @return decay
     */
    double getDecay();

    /**
     * @return sustain
     */
    double getSustain();

    /**
     * @return release
     */
    double getRelease();

    /**
     * setter on command execute on change attack.
     * @param command to set
     */
    void setChangeAttackCommand(Runnable command);

    /**
     * setter on command execute on change decay.
     * @param command to set
     */
    void setChangeDecayCommand(Runnable command);

    /**
     * setter on command execute on change sustain.
     * @param command to set
     */
    void setChangeSustainCommand(Runnable command);

    /**
     * setter on command execute on change release.
     * @param command to set
     */
    void setChangeReleaseCommand(Runnable command);

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
