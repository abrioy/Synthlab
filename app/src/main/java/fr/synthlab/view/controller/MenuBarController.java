package fr.synthlab.view.controller;


import fr.synthlab.view.Workbench;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MenuBarController implements Initializable {
	private static final Logger logger = Logger.getLogger(MenuBarController.class.getName());

	@FXML private MenuBar menuBar;
	private Workbench workbench;
	private Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setWorkbench(Workbench workbench){
		this.workbench = workbench;
	}

	public void setStage(Stage stage){
		this.stage = stage;
	}

	public void onClickFileNew(){

	}

	public void onClickFileOpen() throws IOException {
		FileInputStream file = null;
		try {
			file = new FileInputStream("testfile");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ObjectInputStream inputStream = new ObjectInputStream(file);


		workbench.deSerializeViewModules(inputStream);
		inputStream.close();
	}

	public void onClickFileSave(){

	}

	public void onClickFileSaveAs() throws IOException {
		FileOutputStream file = null;
		try {
			file = new FileOutputStream("testfile");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ObjectOutputStream outputStream = new ObjectOutputStream(file);

		workbench.serializeViewModules(outputStream);
		outputStream.close();
	}

	public void onClickFileExit(){
		stage.fireEvent(
				new WindowEvent(
						stage,
						WindowEvent.WINDOW_CLOSE_REQUEST
				)
		);
	}
}
