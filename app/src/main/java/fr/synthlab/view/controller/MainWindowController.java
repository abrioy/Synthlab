package fr.synthlab.view.controller;

import fr.synthlab.model.module.ModuleType;
import fr.synthlab.view.module.ViewModule;
import fr.synthlab.view.module.ViewModuleFactory;
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
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class MainWindowController implements Initializable {
    private static final Logger LOGGER
            = Logger.getLogger(MainWindowController.class.getName());

    @FXML
    private Workbench workbench;
    @FXML
    private MenuBarController menuBarController;
    @FXML
    private ToolboxController toolboxController;
    @FXML
    private BorderPane mainPane;
    @FXML
    private ScrollPane workbenchScrollPane;

    private ViewModule draggedNewViewModule = null;
    private DoubleProperty zoomLevel
            = new SimpleDoubleProperty(this, null, 1.0d);

    @Override
    public final void initialize(
            final URL location, final ResourceBundle resources) {
        menuBarController.setWorkbench(workbench);
        menuBarController.setMainWindowController(this);
        // Setting the workspace to at least be as big as the scrollpane
        workbenchScrollPane.viewportBoundsProperty()
                .addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                workbench.setMinSize(newValue.getWidth()
                        * zoomLevel.doubleValue(),
                        newValue.getHeight() * zoomLevel.doubleValue());
                Rectangle tempChild = new Rectangle();
                workbench.getChildren().add(tempChild);
                workbench.getChildren().remove(tempChild);
            });
        });
        zoomLevel.addListener((observable, oldValue, newValue) -> {
            final double zoom
                    = Math.min(2, Math.max(0.5, newValue.doubleValue()));
            workbench.setScaleX(1.0d / zoom);
            workbench.setScaleY(1.0d / zoom);
            Platform.runLater(() -> {
                workbench.setMinSize(
                        workbenchScrollPane.getViewportBounds().getWidth()
                                * zoom,
                        workbenchScrollPane.getViewportBounds().getHeight()
                                * zoom);
                Rectangle tempChild = new Rectangle();
                workbench.getChildren().add(tempChild);
                workbench.getChildren().remove(tempChild);
            });
        });
        // Dirty hack to make sure we can drag everywhere on the workbench
        workbench.widthProperty().addListener(
                (observable, oldValue, newValue) -> {
            workbench.requestLayout();
        });
        workbench.heightProperty().addListener(
                (observable, oldValue, newValue) -> {
            workbench.requestLayout();
        });
        // Handling incoming drags from the toolbox
        workbench.setOnDragEntered(event -> {
            Dragboard db = event.getDragboard();
            ModuleType moduleType = null;
            try {
                moduleType = ModuleType.valueOf(
                        ModuleType.getNameFromLong(db.getString()));
            } catch (IllegalArgumentException e) {
                LOGGER.severe(
                        "Unable to drag in module, there is no module called \""
                                + db.getString() + "\".");
            }
            if (moduleType != null) {
                // We create a new module to add to the workbench
                if (draggedNewViewModule == null) {
                    LOGGER.fine("Creating a new module \"" + db.getString()
                            + "\" by dragging it into the workspace.");
                    ViewModule viewModule
                            = ViewModuleFactory.createViewModule(
                            moduleType, workbench);
                    if (viewModule == null) {
                        LOGGER.warning(
                                "Error while creating a ViewModule of type "
                                        + moduleType + ".");
                    } else {
                        draggedNewViewModule = viewModule;
                    }
                } else {
                    LOGGER.fine("Showing back module \"" + db.getString()
                            + "\" because it came back into the workspace.");
                }
                workbench.addModule(draggedNewViewModule);
                // We make it visible only to create a ghost
                draggedNewViewModule.setVisible(true);
                workbench.displayGhost(draggedNewViewModule);
                draggedNewViewModule.setVisible(false);
            }
        });
        workbench.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            if (draggedNewViewModule != null) {
                Point2D localPoint
                        = workbench.sceneToLocal(
                        new Point2D(event.getSceneX(), event.getSceneY()));
                Point2D newLocation
                        = workbench.computeNewModulePosition(
                        draggedNewViewModule, localPoint.getX(),
                        localPoint.getY());
                if (newLocation != null) {
                    draggedNewViewModule.setVisible(true);
                    draggedNewViewModule.relocate(
                            newLocation.getX(), newLocation.getY());
                }
            }
        });
        // Cleaning up if the module get out of the workbench
        workbench.setOnDragExited(event -> {
            if (draggedNewViewModule != null) {
                LOGGER.fine("Hiding module \""
                        + draggedNewViewModule.getModule().getType()
                        + "\" because it got out of the workspace.");
                workbench.hideGhost();
                workbench.removeModule(draggedNewViewModule);
            }
        });
        // The module has been dropped on the workbench
        workbench.setOnDragDropped(event -> {
            if (draggedNewViewModule != null) {
                if (draggedNewViewModule.isVisible()) {
                    LOGGER.fine("Adding module \""
                            + draggedNewViewModule.getModule().getType()
                            + "\" to the workspace.");
                    event.setDropCompleted(true);
                } else {
                    LOGGER.fine("Deleting module \""
                            + draggedNewViewModule.getModule().getType()
                            + "\" because we failed to find+"
                            + " a place for it in the workspace.");
                    event.setDropCompleted(false);
                    workbench.removeModule(draggedNewViewModule);
                }
                draggedNewViewModule = null;
                workbench.hideGhost();
            }
        });
        toolboxController.setOnDragDone(event -> {
            if (draggedNewViewModule != null) {
                // We never found a good position for the module
                LOGGER.fine("Deleting module \""
                        + draggedNewViewModule.getModule().getType()
                        + "\" because we failed to find a"
                        + " place for it in the workspace.");
                workbench.removeModule(draggedNewViewModule);
                draggedNewViewModule = null;
                workbench.hideGhost();
            }
        });
        workbench.setOnScroll(event -> {
            if (event.isControlDown()) {
                double newZoomLevel = zoomLevel.getValue();
                if (event.getDeltaY() < 0) {
                    newZoomLevel += 0.1;
                } else {
                    newZoomLevel -= 0.1;
                }
                zoomLevel.set(newZoomLevel);
                event.consume();
            }
        });
    }

    public final double getZoomLevel() {
        return zoomLevel.get();
    }

    public final DoubleProperty zoomLevelProperty() {
        return zoomLevel;
    }

    public final void setZoomLevel(final double newZoomLevel) {
        zoomLevel.set(newZoomLevel);
    }

    public final void setStageAndSetupListeners(final Stage stage) {
        stage.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                //Point2D localPoint = workbench.sceneToLocal(
                // new Point2D(event.getSceneX(), event.getSceneY()));
                workbench.onRightClick();
            }
        });
        menuBarController.setStage(stage);
    }
}
