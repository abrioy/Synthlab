package fr.synthlab.view.module.output;

import fr.synthlab.view.component.OscilloscopeDrawing;
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
 * view module scope.
 */
public interface ViewModuleSCOP
        extends EventTarget, Styleable, Serializable, Initializable {
    @Override
    void initialize(
            URL url, ResourceBundle resourceBundle);

    /**
     * setter command execute on change scale.
     * @param newPickerCmd runnable
     */
    void setPickerCommand(Runnable newPickerCmd);

    /**
     * @return scale
     */
    int getScale();

    /**
     * @return oscilloscope drawing
     */
    OscilloscopeDrawing getOscilloscopeDrawing();

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
