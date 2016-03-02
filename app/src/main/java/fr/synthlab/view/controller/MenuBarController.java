package fr.synthlab.view.controller;


import fr.synthlab.view.Skin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MenuBarController implements Initializable {
    private static final Logger LOGGER
            = Logger.getLogger(MenuBarController.class.getName());

    @FXML
    private MenuBar menuBar;
    private Workbench workbench;
    private MainWindowController mainWindowController;
    private Stage stage;
    private File currentSaveFile = null;

    @FXML
    private Menu skinMenu;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
    }

    public final void setMainWindowController(
            final MainWindowController newMainWindowController) {
        mainWindowController = newMainWindowController;
		
		ToggleGroup skinToggleGroup = new ToggleGroup();
		Skin currentSkin = mainWindowController.getCurrentSkin();
		for (Skin skin : Skin.values()) {
			RadioMenuItem skinItem = new RadioMenuItem();
			skinItem.setToggleGroup(skinToggleGroup);
			skinItem.setText(skin.getName());
			skinItem.setOnAction(event -> mainWindowController.changeSkin(skin));

			if (skin.equals(currentSkin)) {
				skinItem.setSelected(true);
			}

			skinMenu.getItems().add(skinItem);
		}
    }

    public final void setWorkbench(final Workbench newWorkbench) {
        workbench = newWorkbench;
    }

    public final void setStage(final Stage newStage) {
        stage = newStage;
    }

    public final void onClickViewZoomReset() {
        mainWindowController.setZoomLevel(1.0d);
    }

    public final void onClickViewZoomInc() {
        mainWindowController.setZoomLevel(
                mainWindowController.getZoomLevel() - 0.2d);
    }

    public final void onClickViewZoomDec() {
        mainWindowController.setZoomLevel(
                mainWindowController.getZoomLevel() + 0.2d);
    }


    public final void onClickFileNew() {
        workbench.removeAllModules();
        currentSaveFile = null;
    }

    public final void onClickFileOpen() {
        try {
            FileChooser chooser = createFileBrowser("Open a project.");
            File target = chooser.showOpenDialog(stage);
            openSavedFile(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void onClickFileReload() {
        if (currentSaveFile != null) {
            try {
                openSavedFile(currentSaveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            onClickFileOpen();
        }
    }

    public final void onClickFileSave() {
        if (currentSaveFile != null) {
            try {
                saveToFile(currentSaveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            onClickFileSaveAs();
        }
    }

    public final void onClickFileSaveAs() {
        try {
            FileChooser chooser = createFileBrowser("Save as...");
            File target = chooser.showSaveDialog(stage);
            saveToFile(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void onClickFileExit() {
        stage.fireEvent(
                new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private FileChooser createFileBrowser(final String windowName) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(windowName);

        // Setting the correct extension
        FileChooser.ExtensionFilter extensionFilter
                = new FileChooser.ExtensionFilter(
                "Synthlab project (.syn)", "syn"
        );
        chooser.setSelectedExtensionFilter(extensionFilter);

        // Setting the default path/name
        if (currentSaveFile != null) {
            chooser.setInitialFileName(currentSaveFile.getName());
            chooser.setInitialDirectory(currentSaveFile.getParentFile());
        } else {
            chooser.setInitialFileName("New project.syn");
        }

        return chooser;
    }

    private void openSavedFile(final File file) throws IOException {
        if (file == null) {
            LOGGER.warning("Attempting to load a project from null file.");
        } else {
            LOGGER.info("Loading configuration from file: \"" + file + "\".");
            currentSaveFile = file;

            FileInputStream fileStream = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fileStream);

            workbench.deSerializeViewModules(inputStream);

            inputStream.close();
            fileStream.close();
        }
    }

    private void saveToFile(final File file) throws IOException {
        if (file == null) {
            LOGGER.warning("Attempting to save a project to a null file.");
        } else {
            LOGGER.info("Saving configuration to file: \"" + file + "\".");
            currentSaveFile = file;

            FileOutputStream fileSteam = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileSteam);

            workbench.serializeViewModules(outputStream);
            outputStream.close();
            fileSteam.close();
        }
    }
}

