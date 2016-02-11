package fr.synthlab.view.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
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
	private File currentSaveFile = null;

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
		currentSaveFile = null;
	}

	public void onClickFileOpen() {
		try {
			FileChooser chooser = createFileBrowser("Open a project.");
			File target = chooser.showOpenDialog(stage);
			openSavedFile(target);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onClickFileReload(){
		if(currentSaveFile != null){
			try {
				openSavedFile(currentSaveFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			onClickFileOpen();
		}
	}

	public void onClickFileSave(){
		if(currentSaveFile != null){
			try {
				saveToFile(currentSaveFile);
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
			FileChooser chooser = createFileBrowser("Save as...");
			File target = chooser.showSaveDialog(stage);
			saveToFile(target);
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

	private FileChooser createFileBrowser(String windowName){
		FileChooser chooser = new FileChooser();
		chooser.setTitle(windowName);

		// Setting the correct extension
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
				"Synthlab project (.syn)", "syn"
		);
		chooser.setSelectedExtensionFilter(extensionFilter);

		// Setting the default path/name
		if(currentSaveFile != null) {
			chooser.setInitialFileName(currentSaveFile.getName());
			chooser.setInitialDirectory(currentSaveFile.getParentFile());
		}
		else {
			chooser.setInitialFileName("New project.syn");
		}

		return chooser;
	}

	private void openSavedFile(File file) throws IOException {
		if(file == null) {
			logger.warning("Attempting to load a project from null file.");
		}
		else{
			logger.info("Loading configuration from file: \"" + file + "\".");
			currentSaveFile = file;

			FileInputStream fileStream = new FileInputStream(file);
			ObjectInputStream inputStream = new ObjectInputStream(fileStream);

			workbench.deSerializeViewModules(inputStream);

			inputStream.close();
			fileStream.close();
		}
	}

	private void saveToFile(File file) throws IOException {
		if(file == null) {
			logger.warning("Attempting to save a project to a null file.");
		}
		else {
			logger.info("Saving configuration to file: \"" + file + "\".");
			currentSaveFile = file;

			FileOutputStream fileSteam = new FileOutputStream(file);
			ObjectOutputStream outputStream = new ObjectOutputStream(fileSteam);

			workbench.serializeViewModules(outputStream);
			outputStream.close();
			fileSteam.close();
		}
	}
}
