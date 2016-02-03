package fr.synthlab.view.controller;

import fr.synthlab.model.module.Module;
import fr.synthlab.view.module.ViewModule;
import fr.synthlab.view.module.ViewModuleVCO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.net.URL;
import java.util.ResourceBundle;


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
        workbench.setOnDragExited(event -> {
            logger.log(Level.INFO, "EXIT");
        });
        workbench.setOnDragOver(mouseEvent -> {
            logger.log(Level.INFO, "OVER");
        });
    }
}
