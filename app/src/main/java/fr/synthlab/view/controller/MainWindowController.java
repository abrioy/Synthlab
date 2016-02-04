package fr.synthlab.view.controller;

import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.module.ViewModule;
import fr.synthlab.view.viewModuleFactory.ViewModuleFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.input.Dragboard;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class MainWindowController implements Initializable {
    private static final Logger logger = Logger.getLogger(MainWindowController.class.getName());

    @FXML private Workbench workbench;
	private ViewModule draggedNewViewModule = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

		// Handling incoming drags from the toolbox
        workbench.setOnDragEntered(event -> {
            Dragboard db = event.getDragboard();
			ModuleEnum moduleType = ModuleEnum.valueOf(db.getString());
			if(moduleType == null) {
				logger.warning("Unknown incoming drag: \""+db.getString()+"\" is not a valid module.");
			}
			else {
				// We create a new module to add to the workbench
				if(draggedNewViewModule != null){
					logger.warning("A new module dragged from the toolbox has not properly been added or disposed of.");
					workbench.removeModule(draggedNewViewModule);
					draggedNewViewModule = null;
				}
				ViewModule viewModule = ViewModuleFactory.createViewModule(moduleType);
				if(viewModule == null) {
					logger.warning("Error while creating a ViewModule of type "+moduleType+".");
				}
				else{
					draggedNewViewModule = viewModule;
					// We add the module to the workbench
					workbench.addModule(viewModule);
					viewModule.setVisible(false);
					workbench.displayGhost(viewModule);
				}

			}
        });

		workbench.setOnDragOver(event -> {
			if (draggedNewViewModule != null) {
				Point2D localPoint = workbench.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
				workbench.moveGhost(localPoint.getX(), localPoint.getY());

				Point2D newLocation = workbench.computeNewModulePosition(draggedNewViewModule, localPoint.getX(), localPoint.getY());
				if(newLocation != null){
					draggedNewViewModule.setVisible(true);
					draggedNewViewModule.relocate(newLocation.getX(), newLocation.getY());
				}
			}
		});

		// Cleaning up if the module get out of the workbench
		workbench.setOnDragExited(event -> {
			workbench.removeModule(draggedNewViewModule);
			draggedNewViewModule = null;

			workbench.hideGhost();

		});

    }
}
