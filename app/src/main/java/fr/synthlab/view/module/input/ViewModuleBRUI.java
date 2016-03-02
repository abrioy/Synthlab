package fr.synthlab.view.module.input;

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
public interface ViewModuleBRUI extends EventTarget, Styleable, Serializable, Initializable {
    @Override
    void initialize(URL location, ResourceBundle resources);

    void writeObject(ObjectOutputStream o) throws IOException;

    void readObject(ObjectInputStream o)
            throws IOException, ClassNotFoundException;
}
