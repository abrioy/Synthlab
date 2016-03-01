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
 * Created by miow on 3/1/16.
 */
public interface ViewModuleSCOP extends EventTarget, Styleable, Serializable, Initializable {
	@Override
	void initialize(
			URL url, ResourceBundle resourceBundle);

	void setPickerCommand(Runnable newPickerCmd);

	int getScale();

	OscilloscopeDrawing getOscilloscopeDrawing();

	void writeObject(ObjectOutputStream o)
					throws IOException;

	void readObject(ObjectInputStream o)
							throws IOException, ClassNotFoundException;
}
