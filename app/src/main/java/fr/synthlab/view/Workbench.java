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
import fr.synthlab.view.module.ViewModuleVCO;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.logging.Logger;

public class Workbench extends Pane {
	private static final Logger logger = Logger.getLogger(Workbench.class.getName());

	private Rectangle dragGhost = new Rectangle();

	public Workbench() {

		dragGhost.setStroke(Color.GREEN);
		dragGhost.setFill(Color.TRANSPARENT);


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


	private void makeDraggable(ViewModule module) {
		final Workbench workbench = this;

		class Delta {
			double x, y;
		}
		final Delta mouseDelta = new Delta();

		module.setOnMousePressed(event -> {
			Point2D localPoint = module.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
			mouseDelta.x = localPoint.getX();
			mouseDelta.y = localPoint.getY();


			module.toFront();
			// Creating a ghost image
			dragGhost.setWidth(module.getBoundsInParent().getWidth());
			dragGhost.setHeight(module.getBoundsInParent().getHeight());
			dragGhost.toFront();
			workbench.getChildren().add(dragGhost);

			event.consume();
		});

		module.setOnMouseReleased(mouseEvent -> {
			module.setCursor(Cursor.HAND);

			workbench.getChildren().remove(dragGhost);
		});

		module.setOnMouseDragged(event -> {
			Point2D localPoint = workbench.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));

			moveModule(module, localPoint.getX() - mouseDelta.x, localPoint.getY() - mouseDelta.y);

			event.consume();
		});

		module.setOnMouseEntered(mouseEvent -> module.setCursor(Cursor.HAND));
	}

	/**
	 * Checks if a Bounds is colliding with any module except the one provided in the parameter
	 * @param node The module to exclude for collision checking
	 * @param bounds The bounds to check
	 * @return The Bounds of the first colliding module if there any
	 */
	private Bounds checkCollisions(Node node, Bounds bounds) {
		for (Node child : this.getChildren()) {
			if (child != dragGhost && node != child) {
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

	private void moveModule(ViewModule node, double expectedX, double expectedY) {
		double newX, newY;

		Bounds oldBounds = node.getBoundsInParent();
		Bounds newBounds = new BoundingBox(
				expectedX,
				expectedY,
				oldBounds.getWidth(),
				oldBounds.getHeight()
		);

		Bounds collidingBounds = checkCollisions(node, newBounds);
		if (collidingBounds == null) {
			// The new position is not colliding with something
			newX = expectedX;
			newY = expectedY;
		}
		else {
			// Snapping to the colliding bounds

			// Finding out where to snap the node
			Point2D newCenter = getBoundsCenter(newBounds);
			Point2D collidingNodeCenter = getBoundsCenter(collidingBounds);

			double distanceX = Math.abs(newCenter.getX() - collidingNodeCenter.getX());
			double distanceY = Math.abs(newCenter.getY() - collidingNodeCenter.getY());

			if(distanceX > distanceY){
				// We need to push it along the X axis
				newY = expectedY;
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
				newX = expectedX;
				if(newCenter.getY() > collidingNodeCenter.getY()){
					// Bottom
					newY = collidingBounds.getMaxY() + 1;
				}
				else{
					// Top
					newY = collidingBounds.getMinY() - newBounds.getHeight() - 1;
				}
			}

		}

		// Moving the ghost to where the module should be
		dragGhost.relocate(expectedX, expectedY);

		// Snapping the module in a non-colliding position
		node.relocate(newX, newY);

	}
}
