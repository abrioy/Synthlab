package fr.synthlab.view.controller;

import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.module.ViewModule;
import fr.synthlab.view.viewModuleFactory.ViewModuleFactory;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class MainWindowController implements Initializable {
    private static final Logger logger = Logger.getLogger(MainWindowController.class.getName());

    @FXML private Workbench workbench;
	@FXML private ToolboxController toolboxController;
	@FXML private BorderPane mainPane;
	@FXML private ScrollPane workbenchScrollPane;

	private ViewModule draggedNewViewModule = null;

	private DoubleProperty zoomLevel = new SimpleDoubleProperty(this, null, 1.0d);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

		// Setting the workspace to at least be as big as the scrollpane
		workbenchScrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
			Platform.runLater(()-> {
				workbench.setMinSize(newValue.getWidth() * zoomLevel.doubleValue(),
						newValue.getHeight() * zoomLevel.doubleValue());


				// Hack to force a layout refreshgit
				Rectangle tempChild = new Rectangle();
				workbench.getChildren().add(tempChild);
				workbench.getChildren().remove(tempChild);
			});
		});

		zoomLevel.addListener((observable, oldValue, newValue) -> {
			workbench.setScaleX(1.0d / newValue.doubleValue());
			workbench.setScaleY(1.0d / newValue.doubleValue());

			Platform.runLater(()-> {
				workbench.setMinSize(workbenchScrollPane.getViewportBounds().getWidth() * newValue.doubleValue(),
						workbenchScrollPane.getViewportBounds().getHeight() * newValue.doubleValue());

				// Hack to force a layout refresh
				Rectangle tempChild = new Rectangle();
				workbench.getChildren().add(tempChild);
				workbench.getChildren().remove(tempChild);
			});
		});



		// Handling incoming drags from the toolbox
        workbench.setOnDragEntered(event -> {
            Dragboard db = event.getDragboard();
			ModuleEnum moduleType = null;
			try{
				moduleType = ModuleEnum.valueOf(ModuleEnum.getNameFromLong(db.getString()));
			}
			catch(IllegalArgumentException e){
				logger.severe("Unable to drag in module, there is no module called \""+db.getString()+"\".");
			}

			if(moduleType != null) {
				// We create a new module to add to the workbench
				if(draggedNewViewModule == null){
					logger.fine("Creating a new module \""+db.getString()+"\" by dragging it into the workspace.");
					ViewModule viewModule = ViewModuleFactory.createViewModule(moduleType, workbench);
					if(viewModule == null) {
						logger.warning("Error while creating a ViewModule of type "+moduleType+".");
					}
					else{
						draggedNewViewModule = viewModule;
					}
				}
				else{
					logger.fine("Showing back module \""+db.getString()+"\" because it came back into the workspace.");
				}

				// We add the module to the workbench
				workbench.addModule(draggedNewViewModule);

				// We make it visible only to create a ghost
				draggedNewViewModule.setVisible(true);
				workbench.displayGhost(draggedNewViewModule);

				draggedNewViewModule.setVisible(false);

			}
        });

		workbench.setOnDragOver(event -> {
			if (draggedNewViewModule != null) {
				Point2D localPoint = workbench.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));

				Point2D newLocation = workbench.computeNewModulePosition(draggedNewViewModule, localPoint.getX(), localPoint.getY());
				if(newLocation != null){
					draggedNewViewModule.setVisible(true);
					draggedNewViewModule.relocate(newLocation.getX(), newLocation.getY());
				}
			}
		});

		// Cleaning up if the module get out of the workbench
		workbench.setOnDragExited(event -> {
			if(draggedNewViewModule != null) {
				logger.fine("Hiding module \"" + draggedNewViewModule.getModule().getType() +
						"\" because it got out of the workspace.");
				workbench.hideGhost();
				workbench.removeModule(draggedNewViewModule);
			}
		});

		toolboxController.setOnDragDone(type -> {
			if (draggedNewViewModule != null) {
				if (!draggedNewViewModule.isVisible()) {
					// We never found a good position for the module
					logger.fine("Deleting module \"" + draggedNewViewModule.getModule().getType() +
							"\" because we failed to find a place for it in the workspace.");
					workbench.removeModule(draggedNewViewModule); // FIXME: Does not delete the module
				} else {
					logger.fine("Adding module \"" + draggedNewViewModule.getModule().getType() +
							"\" to the workspace.");
					workbench.addModule(draggedNewViewModule);
				}
			}

			draggedNewViewModule = null;
			workbench.hideGhost();
		});

		workbench.setOnScroll(event -> {
			if(event.isControlDown()) {
				double newZoomLevel = zoomLevel.getValue();
				if (event.getDeltaY() < 0) {
					newZoomLevel += 0.1;
				} else {
					newZoomLevel -= 0.1;
				}
				newZoomLevel = Math.max(0.5, newZoomLevel);
				newZoomLevel = Math.min(2, newZoomLevel);

				zoomLevel.set(newZoomLevel);
				event.consume();
			}
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
