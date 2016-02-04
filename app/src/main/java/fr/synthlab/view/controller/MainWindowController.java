package fr.synthlab.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class MainWindowController implements Initializable {
	private static final Logger logger = Logger.getLogger(MainWindowController.class.getName());

	@FXML Workbench workbench;
	@FXML BorderPane mainPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainPane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			Point2D localPoint = workbench.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
			logger.info("to X:" + localPoint.getX() + " Y:"+localPoint.getY());
		});
	}
}
