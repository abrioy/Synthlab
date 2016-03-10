package fr.synthlab.view.controller;

import fr.synthlab.view.Skin;
import fr.synthlab.view.controller.workbench.Workbench;
import fr.synthlab.view.controller.workbench.WorkbenchSerializer;
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

/**
 * controller for menu bar.
 */
public class MenuBarController implements Initializable {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(MenuBarController.class.getName());
    /**
     * view of menu bar.
     */
    @FXML
    private MenuBar menuBar;
    /**
     * current workbench.
     */
    private Workbench workbench;
    /**
     * main windows.
     */
    private MainWindowController mainWindowController;
    /**
     * stage.
     */
    private Stage stage;
    /**
     * save of workbench.
     */
    private File currentSaveFile = null;
    /**
     * menu.
     */
    @FXML
    private Menu skinMenu;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
    }

    /**
     * setter on main windows.
     * @param newMainWindowController to set
     */
    public final void setMainWindowController(
            final MainWindowController newMainWindowController) {
        mainWindowController = newMainWindowController;

        ToggleGroup skinToggleGroup = new ToggleGroup();
        Skin currentSkin = mainWindowController.getCurrentSkin();
        for (Skin skin : Skin.values()) {
            RadioMenuItem skinItem = new RadioMenuItem();
            skinItem.setToggleGroup(skinToggleGroup);
            skinItem.setText(skin.getName());
            skinItem.setOnAction(
                    event -> mainWindowController.changeSkin(skin));

            if (skin.equals(currentSkin)) {
                skinItem.setSelected(true);
            }

            skinMenu.getItems().add(skinItem);
        }
    }

    /**
     * setter on workbench.
     * @param newWorkbench to set
     */
    public final void setWorkbench(final Workbench newWorkbench) {
        workbench = newWorkbench;
    }

    /**
     * setter on stage.
     * @param newStage to set
     */
    public final void setStage(final Stage newStage) {
        stage = newStage;
    }

    /**
     * zoom event reset.
     */
    public final void onClickViewZoomReset() {
        mainWindowController.setZoomLevel(1.0d);
    }

    /**
     * zoom event inc.
     */
    public final void onClickViewZoomInc() {
        mainWindowController.setZoomLevel(
                mainWindowController.getZoomLevel() - 0.2d);
    }

    /**
     * zoom event dec.
     */
    public final void onClickViewZoomDec() {
        mainWindowController.setZoomLevel(
                mainWindowController.getZoomLevel() + 0.2d);
    }

    /**
     * new file event.
     */
    public final void onClickFileNew() {
        workbench.removeAllModules();
        currentSaveFile = null;
    }

    /**
     * file open event.
     */
    public final void onClickFileOpen() {
        try {
            FileChooser chooser = createFileBrowser("Open a project.");
            File target = chooser.showOpenDialog(stage);
            openSavedFile(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reload file event.
     */
    public final void onClickFileReload() {
        if (currentSaveFile != null) {
            try {
                openSavedFile(currentSaveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            onClickFileSaveAs();
        }
    }

    /**
     * file save event.
     */
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

    /**
     * file save as event.
     */
    public final void onClickFileSaveAs() {
        try {
            FileChooser chooser = createFileBrowser("Save as...");
            File target = chooser.showSaveDialog(stage);
            saveToFile(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * exit event.
     */
    public final void onClickFileExit() {
        stage.fireEvent(
                new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * file chooser windows.
     * @param windowName name of windows
     * @return javafx file chooser
     */
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

    /**
     * open saved file.
     * @param file file open
     * @throws IOException write or read error
     */
    private void openSavedFile(final File file) throws IOException {
        if (file == null) {
            LOGGER.warning("Attempting to load a project from null file.");
        } else {
            LOGGER.info("Loading configuration from file: \"" + file + "\".");
            currentSaveFile = file;

            FileInputStream fileStream = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fileStream);

            WorkbenchSerializer.deSerializeViewModules(workbench, inputStream);

            inputStream.close();
            fileStream.close();
        }
    }

    /**
     * save into file.
     * @param file to save
     * @throws IOException write or read error
     */
    private void saveToFile(final File file) throws IOException {
        if (file == null) {
            LOGGER.warning("Attempting to save a project to a null file.");
        } else {
            LOGGER.info("Saving configuration to file: \"" + file + "\".");
            currentSaveFile = file;

            FileOutputStream fileSteam = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileSteam);

            WorkbenchSerializer.serializeViewModules(workbench, outputStream);
            outputStream.close();
            fileSteam.close();
        }
    }
}

