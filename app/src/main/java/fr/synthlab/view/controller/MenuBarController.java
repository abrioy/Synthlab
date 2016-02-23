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

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MenuBarController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(MenuBarController.class.getName());

    @FXML
    private MenuBar menuBar;
    private Workbench workbench;
    private MainWindowController mainWindowController;
    private Stage stage;
    private File currentSaveFile = null;

    @FXML
    private Menu skinMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setMainWindowController(MainWindowController newMainWindowController) {
        mainWindowController = newMainWindowController;
    }

    public void setWorkbench(Workbench newWorkbench) {
        workbench = newWorkbench;

        ToggleGroup skinToggleGroup = new ToggleGroup();
        Skin currentSkin = workbench.getCurrentSkin();
        for (Skin skin : Skin.values()) {
            RadioMenuItem skinItem = new RadioMenuItem();
            skinItem.setToggleGroup(skinToggleGroup);
            skinItem.setText(skin.getName());
            skinItem.setOnAction(event -> workbench.changeSkin(skin));

            if (skin.equals(currentSkin)) {
                skinItem.setSelected(true);
            }

            skinMenu.getItems().add(skinItem);
        }
    }

    public void setStage(Stage newStage) {
        stage = newStage;
    }

    public void onClickViewZoomReset() {
        mainWindowController.setZoomLevel(1.0d);
    }

    public void onClickViewZoomInc() {
        mainWindowController.setZoomLevel(mainWindowController.getZoomLevel() + 0.2d);
    }

    public void onClickViewZoomDec() {
        mainWindowController.setZoomLevel(mainWindowController.getZoomLevel() - 0.2d);
    }


    public void onClickFileNew() {
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

    public void onClickFileReload() {
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

    public void onClickFileSave() {
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

    public void onClickFileSaveAs() {
        try {
            FileChooser chooser = createFileBrowser("Save as...");
            File target = chooser.showSaveDialog(stage);
            saveToFile(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickFileExit() {
        stage.fireEvent(
                new WindowEvent(
                        stage,
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
    }

    private FileChooser createFileBrowser(String windowName) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(windowName);

        // Setting the correct extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
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

    private void openSavedFile(File file) throws IOException {
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

    private void saveToFile(File file) throws IOException {
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

