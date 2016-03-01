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
public interface ViewModuleEG extends EventTarget, Styleable, Serializable, Initializable {
	@Override
	void initialize(
			URL location, ResourceBundle resources);

	double getAttack();

	double getDecay();

	double getSustain();

	double getRelease();

	void setChangeAttackCommand(Runnable command);

	void setChangeDecayCommand(Runnable command);

	void setChangeSustainCommand(Runnable command);

	void setChangeReleaseCommand(Runnable command);

	void writeObject(ObjectOutputStream o)
					throws IOException;

	void readObject(ObjectInputStream o)
							throws IOException, ClassNotFoundException;
}
