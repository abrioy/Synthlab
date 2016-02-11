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
	private String currentSaveFilePath = null;

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
		workbench.removeAllModules();
		currentSaveFilePath = null;
	}

	public void onClickFileOpen() {
		try {
			openSavedFile("testfile");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onClickFileSave(){
		if(currentSaveFilePath != null){
			try {
				saveToFile(currentSaveFilePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			onClickFileSaveAs();
		}
	}

	public void onClickFileSaveAs() {
		try {
			saveToFile("testfile");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onClickFileExit(){
		stage.fireEvent(
				new WindowEvent(
						stage,
						WindowEvent.WINDOW_CLOSE_REQUEST
				)
		);
	}


	private void openSavedFile(String path) throws IOException {
		logger.info("Loading configuration from file: \""+path+"\".");
		currentSaveFilePath = path;

		FileInputStream file = new FileInputStream(path);
		ObjectInputStream inputStream = new ObjectInputStream(file);

		workbench.deSerializeViewModules(inputStream);

		inputStream.close();
		file.close();
	}

	private void saveToFile(String path) throws IOException {
		logger.info("Saving configuration to file: \""+path+"\".");
		currentSaveFilePath = path;

		FileOutputStream file = new FileOutputStream(path);

		ObjectOutputStream outputStream = new ObjectOutputStream(file);

		workbench.serializeViewModules(outputStream);
		outputStream.close();
		file.close();
	}
}
