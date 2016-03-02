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
 * Created by miow on 3/1/16.
 */
public interface ViewModuleSEQ extends EventTarget, Styleable, Serializable, Initializable, Observer {
    void setResetCommand(Runnable reset);

    void setChangeStep1Command(Runnable command);

    void setChangeStep2Command(Runnable command);

    void setChangeStep3Command(Runnable command);

    void setChangeStep4Command(Runnable command);

    void setChangeStep5Command(Runnable command);

    void setChangeStep6Command(Runnable command);

    void setChangeStep7Command(Runnable command);

    void setChangeStep8Command(Runnable command);

    double getStepValue(int step);

    @Override
    void initialize(
            URL location, ResourceBundle resources);

    void writeObject(ObjectOutputStream o)
                    throws IOException;

    void readObject(ObjectInputStream o)
                            throws IOException, ClassNotFoundException;

    @Override
    void update(Observable o, Object arg);
}
