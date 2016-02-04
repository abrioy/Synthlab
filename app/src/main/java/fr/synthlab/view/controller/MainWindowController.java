package fr.synthlab.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainWindowController implements Initializable {
    private static final Logger logger = Logger.getLogger(MainWindowController.class.getName());

    @FXML private Pane workbench;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        workbench.setOnDragEntered(event -> {
            Dragboard db = event.getDragboard();
            logger.log(Level.INFO, db.getString());
            logger.log(Level.INFO, "ENTER");
        });
        workbench.setOnDragOver(mouseEvent -> {
            mouseEvent.acceptTransferModes(TransferMode.MOVE);
        });
        workbench.setOnDragDropped(event -> {
            logger.log(Level.INFO, "DROPPED");
        });
    }
}
