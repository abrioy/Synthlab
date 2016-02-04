package fr.synthlab.view.controller;


import fr.synthlab.model.module.moduleFactory.ModuleFactory;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.out.ModuleOut;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import fr.synthlab.view.component.OscilloscopeDrawing;
import fr.synthlab.view.module.ViewModule;
import fr.synthlab.view.module.ViewModuleOUT;
import fr.synthlab.view.module.ViewModuleOscilloscope;
import fr.synthlab.view.module.ViewModuleVCO;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.Scanner;
import java.util.logging.Logger;

public class Workbench extends Pane {
	private static final Logger logger = Logger.getLogger(Workbench.class.getName());

	private ImageView dragGhost = new ImageView();

	//
    private Boolean dragCable = false;

	public Workbench() {

		// Making the ghost a bit spookier
        //so spooky
		dragGhost.setOpacity(0.40d);


		ViewModule module = new ViewModuleVCO(this);
		addModule(module);
		ViewModuleOUT out = new ViewModuleOUT(this);
		addModule(out);
		ViewModuleOscilloscope viewOscilloscope = new ViewModuleOscilloscope(this);
		addModule(viewOscilloscope);

		ModuleVCOA vcoa = ModuleFactory.createVCO();
		ModuleVCOA vcoa2 = ModuleFactory.createVCO();

		vcoa2.setFrequency(1);




		// Add an output mixer.
		ModuleOut sound = ModuleFactory.createOut();

		ModuleOscilloscope oscillo = ModuleFactory.createOscilloscope();

		ModuleFactory.getSyn().start();

		OutputPort squarePort = (OutputPort) vcoa.getPort("out");

		InputPort inOsc = (InputPort) oscillo.getPort("in");
		OutputPort outOsc = (OutputPort) oscillo.getPort("out");
		InputPort fm1 = (InputPort) vcoa.getPort("fm");
		OutputPort trianglePort2 = (OutputPort) vcoa2.getPort("out");


		// Connect square output to oscillo in
		squarePort.connect(inOsc);

		// Connect oscillo out to sound
		outOsc.connect(sound.getPort("in"));

		// Connect 2nd VCOA output to 1st VCOA input
		trianglePort2.connect(fm1);

		vcoa.start();
		vcoa2.start();
		oscillo.start();
		sound.start();
		//this.getChildren().add(new OscilloscopeDrawing(oscillo));

		Scanner sc = new Scanner(System.in);
		Thread t = new Thread(() -> {
			while (true) {
				String f = sc.next();
				String[] res = f.split("/");
				if (res[0].equals("1")) {
					vcoa.setFrequency(Integer.parseInt(res[1]));
				} else if (res[0].equals("2")) {
					vcoa2.setFrequency(Integer.parseInt(res[1]));
				} else if (res[0].equals("s")) {
					vcoa.setShape("sawtooth");
				} else if (res[0].equals("c")) {
					vcoa.setShape("square");
				} else if (res[0].equals("t")) {
					vcoa.setShape("triangle");
				}
			}
		});

		t.start();
		((OscilloscopeDrawing) ((AnchorPane) viewOscilloscope.getChildren().get(0)).getChildren().get(0)).setModuleOscillo(oscillo);
	}

	public void onRightClick() {
		logger.info("RIGHT CLICK");
	}

    public void plugClicked(){
        logger.info("PLUG CLICKED");
    }

	private void addModule(ViewModule module) {
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
			WritableImage snapshot = module.snapshot(new SnapshotParameters(), null);
			dragGhost.setImage(snapshot);
			dragGhost.toFront();

			// Initial position of the ghost
			Bounds moduleBounds = module.getBoundsInParent();
			dragGhost.relocate(moduleBounds.getMinX(), moduleBounds.getMinY());
			workbench.getChildren().add(dragGhost);

		});

		module.setOnMouseReleased(mouseEvent -> {
			module.setCursor(Cursor.HAND);

			workbench.getChildren().remove(dragGhost);
		});

		module.setOnMouseDragged(event -> {
			Point2D localPoint = workbench.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));

			moveModule(module, localPoint.getX() - mouseDelta.x, localPoint.getY() - mouseDelta.y);

		});

		module.setOnMouseEntered(mouseEvent -> module.setCursor(Cursor.HAND));
	}

	/**
	 * Checks if a Bounds is colliding with any module except the one provided in the parameter
	 *
	 * @param node   The module to exclude for collision checking
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
	 *
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
		// Moving the ghost to where the module should be
		dragGhost.relocate(expectedX, expectedY);

		double newX = expectedX;
		double newY = expectedY;

		// We will try 3 times to find a place for the node
		for (int i = 0; i < 3; i++) {
			if(newX < 0){
				newX = 0;
			}
			if(newY < 0){
				newY = 0;
			}

			Bounds oldBounds = node.getBoundsInParent();
			Bounds newBounds = new BoundingBox(
					newX,
					newY,
					oldBounds.getWidth(),
					oldBounds.getHeight()
			);

			Bounds collidingBounds = checkCollisions(node, newBounds);
			if (collidingBounds == null) {
				// The new position is not colliding with something
				// We move the node
				node.relocate(newX, newY);
				return;
			} else {
				// Snapping to the colliding bounds

				// Finding out where to put back the node
				Point2D newCenter = getBoundsCenter(newBounds);
				Point2D collidingNodeCenter = getBoundsCenter(collidingBounds);

				double distanceX = Math.abs(newCenter.getX() - collidingNodeCenter.getX());
				double distanceY = Math.abs(newCenter.getY() - collidingNodeCenter.getY());

				if (distanceX > distanceY) {
					// We need to push it along the X axis
					if (newCenter.getX() > collidingNodeCenter.getX()) {
						// Right
						newX = collidingBounds.getMaxX() + 1;
					} else {
						// Left
						newX = collidingBounds.getMinX() - newBounds.getWidth() - 1;
					}
				} else {
					// We need to push it along the Y axis
					if (newCenter.getY() > collidingNodeCenter.getY()) {
						// Bottom
						newY = collidingBounds.getMaxY() + 1;
					} else {
						// Top
						newY = collidingBounds.getMinY() - newBounds.getHeight() - 1;
					}
				}

			}

		}
		// The loop didn't succeed in finding a non-colliding location, we don't move the node

	}
}
