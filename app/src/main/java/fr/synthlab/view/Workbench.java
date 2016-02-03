package fr.synthlab.view;


import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.out.ModuleOut;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import fr.synthlab.view.module.ViewModule;
import fr.synthlab.view.module.ViewModuleOUT;
import fr.synthlab.view.module.ViewModuleOscillator;
import fr.synthlab.view.module.ViewModuleVCO;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.logging.Logger;

public class Workbench extends Pane {
	private static final Logger logger = Logger.getLogger(Workbench.class.getName());

	public Workbench() {
		ViewModule module = new ViewModuleVCO();
		addModule(module);
		ViewModuleOUT out = new ViewModuleOUT();
		addModule(out);



		// SCOP init
		Synthesizer synth = JSyn.createSynthesizer();

		ModuleVCOA vcoa = new ModuleVCOA(synth);
		// Add an output mixer.
		ModuleOut b = new ModuleOut(synth);

		ModuleOscilloscope oscillo = new ModuleOscilloscope(synth);

		synth.start();

		Port p = vcoa.getPort("square");
		((OutputPort) p).connect(b.getInput());
		oscillo.connect((UnitOutputPort)((OutputPort) p).getOutput());

		b.start();
		//addModule(new ViewModuleOscillator(oscillo));
	}

	private void addModule(ViewModule module){
		this.getChildren().add(module);
		makeDraggable(module);
	}


	private void makeDraggable(Node node) {
		// Making the frame draggable
		class mouseDelta {
			double x ;
			double y ;
		}

		mouseDelta delta = new mouseDelta();
		node.setOnMousePressed(event -> {
			delta.x = event.getSceneX() ;
			delta.y = event.getSceneY() ;
		});
		node.setOnMouseDragEntered(event -> node.setCursor(Cursor.MOVE));
		node.setOnMouseReleased(mouseEvent -> {
			node.setCursor(Cursor.HAND);
		});

		node.setOnMouseDragged(event -> {
			double deltaX = event.getSceneX() - delta.x ;
			double deltaY = event.getSceneY() - delta.y ;

			moveModule(node, deltaX, deltaY);
			delta.x = event.getSceneX();
			delta.y = event.getSceneY();

		});

		node.setOnMouseEntered(mouseEvent -> node.setCursor(Cursor.HAND));
	}

	/**
	 * Checks if a Bounds is colliding with any module except the one provided in the parameter
	 * @param node The module to exclude for collision checking
	 * @param bounds The bounds to check
	 * @return The Bounds of the first colliding module if there any
	 */
	private Bounds checkCollisions(Node node, Bounds bounds) {
		for (Node child : this.getChildren()) {
			if (node != child) {
				Bounds childBounds = child.getBoundsInParent();

				if (bounds.intersects(childBounds)) {
					return childBounds;
				}
			}
		}
		return null;
	}

	/**
	 * Computes the 2D center of a Bounds object
	 * @param bounds
	 * @return The center of the rectangle
	 */
	private Point2D getBoundsCenter(Bounds bounds) {
		double x, y;
		x = bounds.getMinX() + (bounds.getWidth() / 2.0d);
		y = bounds.getMinY() + (bounds.getHeight() / 2.0d);

		return new Point2D(x, y);
	}

	private void moveModule(Node node, double deltaX, double deltaY) {
		Bounds oldBounds = node.getBoundsInParent();
		Bounds newBounds = new BoundingBox(
				oldBounds.getMinX() + deltaX,
				oldBounds.getMinY() + deltaY,
				oldBounds.getWidth(),
				oldBounds.getHeight()
		);

		Bounds collidingBounds = checkCollisions(node, newBounds);
		if (collidingBounds == null) {
			// The new position is not colliding with something
			node.relocate(node.getLayoutX() + deltaX, node.getLayoutY() + deltaY);
		}
		else {
			// Snapping to the colliding bounds

			// Finding out where to snap the node
			Point2D newCenter = getBoundsCenter(newBounds);
			Point2D collidingNodeCenter = getBoundsCenter(collidingBounds);

			double distanceX = Math.abs(newCenter.getX() - collidingNodeCenter.getX());
			double distanceY = Math.abs(newCenter.getY() - collidingNodeCenter.getY());

			double newX, newY;

			if(distanceX > distanceY){
				// We need to push it along the X axis
				newY = node.getLayoutY() + deltaY;
				if(newCenter.getX() > collidingNodeCenter.getX()){
					// Right
					newX = collidingBounds.getMaxX() + 1;
				}
				else{
					// Left
					newX = collidingBounds.getMinX() - newBounds.getWidth() - 1;
				}
			}
			else {
				// We need to push it along the Y axis
				newX = node.getLayoutX() + deltaX;
				if(newCenter.getY() > collidingNodeCenter.getY()){
					// Bottom
					newY = collidingBounds.getMaxY() + 1;
				}
				else{
					// Top
					newY = collidingBounds.getMinY() - newBounds.getHeight() - 1;
				}
			}

			node.relocate(newX, newY);
		}
	}
}
