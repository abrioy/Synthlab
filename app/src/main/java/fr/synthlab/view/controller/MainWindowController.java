package fr.synthlab.view.controller;


import fr.synthlab.view.Workbench;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.net.URL;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {
    private static final Logger logger = Logger.getLogger(MainWindowController.class.getName());


	@FXML private BorderPane mainPane;
    @FXML private Workbench workbench;
    @FXML private ScrollPane workbenchScrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        workbench.setOnDragEntered(event -> {
            Dragboard db = event.getDragboard();
            logger.log(Level.INFO, db.getString());
            logger.log(Level.INFO, "ENTER");
        });
        workbench.setOnDragExited(event -> {
            logger.log(Level.INFO, "EXIT");
        });
        workbench.setOnDragOver(mouseEvent -> {
            mouseEvent.acceptTransferModes(TransferMode.MOVE);
        });
        workbench.setOnDragDropped(event -> {
            logger.log(Level.INFO, "DROPPED");
        });
    }

	public void setStageAndSetupListeners(Stage stage) {
		stage.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                //Point2D localPoint = workbench.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
                workbench.onRightClick();
            }
        });
	}
}
