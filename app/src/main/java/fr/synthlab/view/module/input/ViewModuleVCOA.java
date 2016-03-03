package fr.synthlab.view.module.input;

import fr.synthlab.model.module.vcoa.ShapeVCOA;
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
public interface ViewModuleVCOA
        extends EventTarget, Styleable, Serializable, Initializable {
    @Override
    void initialize(
            URL url, ResourceBundle resourceBundle);

    void setChangeShapeCommand(Runnable changeShape);

    void setChangeFreqCommand(Runnable changeFreq);

    double getFreq();

    ShapeVCOA getSelectedShape();

    void writeObject(ObjectOutputStream o)
                    throws IOException;

    void readObject(ObjectInputStream o)
            throws IOException, ClassNotFoundException;
}
