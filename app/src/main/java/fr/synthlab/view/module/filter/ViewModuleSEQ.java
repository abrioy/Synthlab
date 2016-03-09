package fr.synthlab.view.module.filter;

import javafx.css.Styleable;
import javafx.event.EventTarget;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * View module sequencer.
 */
public interface ViewModuleSEQ
        extends EventTarget, Styleable, Serializable, Initializable, Observer {
    /**
     * setter on command execute on reset.
     * @param reset to set
     */
    void setResetCommand(Runnable reset);

    /**
     * setter on command execute on step come to 1.
     * @param command to set
     */
    void setChangeStep1Command(Runnable command);

    /**
     * setter on command execute on step come to 2.
     * @param command to set
     */
    void setChangeStep2Command(Runnable command);

    /**
     * setter on command execute on step come to 3.
     * @param command to set
     */
    void setChangeStep3Command(Runnable command);

    /**
     * setter on command execute on step come to 4.
     * @param command to set
     */
    void setChangeStep4Command(Runnable command);

    /**
     * setter on command execute on step come to 5.
     * @param command to set
     */
    void setChangeStep5Command(Runnable command);

    /**
     * setter on command execute on step come to 6.
     * @param command to set
     */
    void setChangeStep6Command(Runnable command);

    /**
     * setter on command execute on step come to 7.
     * @param command to set
     */
    void setChangeStep7Command(Runnable command);

    /**
     * setter on command execute on step come to 8.
     * @param command to set
     */
    void setChangeStep8Command(Runnable command);

    /**
     * getter on value of sequence number step.
     * @param step index of value search
     * @return value
     */
    double getStepValue(int step);

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

    @Override
    void update(Observable o, Object arg);
}
