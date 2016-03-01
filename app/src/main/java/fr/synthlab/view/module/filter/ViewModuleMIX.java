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
public interface ViewModuleMIX extends EventTarget, Styleable, Serializable, Initializable {
	/**
	 * getter on value attenution of input 1.
	 *
	 * @return value
	 */
	double getAttenuator1();

	/**
	 * getter on value attenution of input 2.
	 *
	 * @return value
	 */
	double getAttenuator2();

	/**
	 * getter on value attenution of input 3.
	 *
	 * @return value
	 */
	double getAttenuator3();

	/**
	 * getter on value attenution of input 4.
	 *
	 * @return value
	 */
	double getAttenuator4();

	/**
	 * setter command on change attenuation input 1.
	 *
	 * @param newAttenuator1Cmd command
	 */
	void setAttenuator1Cmd(Runnable newAttenuator1Cmd);

	/**
	 * setter command on change attenuation input 2.
	 *
	 * @param newAttenuator2Cmd command
	 */
	void setAttenuator2Cmd(Runnable newAttenuator2Cmd);

	/**
	 * setter command on change attenuation input 3.
	 *
	 * @param newAttenuator3Cmd command
	 */
	void setAttenuator3Cmd(Runnable newAttenuator3Cmd);

	/**
	 * setter command on change attenuation input 4.
	 *
	 * @param newAttenuator4Cmd command
	 */
	void setAttenuator4Cmd(Runnable newAttenuator4Cmd);

	/**
	 * initialise command.
	 *
	 * @param location  URL
	 * @param resources ResourceBundle
	 */
	@Override
	void initialize(
			URL location, ResourceBundle resources);

	void writeObject(ObjectOutputStream o)
					throws IOException;

	void readObject(ObjectInputStream o)
							throws IOException, ClassNotFoundException;
}
