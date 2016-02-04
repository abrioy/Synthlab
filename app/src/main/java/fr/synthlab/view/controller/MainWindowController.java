package fr.synthlab.view.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

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
import java.util.logging.Logger;


public class MainWindowController implements Initializable {
    private static final Logger logger = Logger.getLogger(MainWindowController.class.getName());


	@FXML private BorderPane mainPane;
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

		mainPane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			Point2D localPoint = workbench.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
			logger.info("to X:" + localPoint.getX() + " Y:"+localPoint.getY());
		});
    }

}
