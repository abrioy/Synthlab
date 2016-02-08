package fr.synthlab.view;


import fr.synthlab.model.module.moduleFactory.ModuleFactory;
import fr.synthlab.model.module.port.Port;
import fr.synthlab.view.component.Cable;
import fr.synthlab.view.component.Plug;
import fr.synthlab.view.module.ViewModule;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class Workbench extends Pane {
	private static final Logger logger = Logger.getLogger(Workbench.class.getName());

	private ImageView dragGhost = new ImageView();
    private Cable draggedCable;

	public Workbench() {

		// Making the ghost a bit spookier
		dragGhost.setOpacity(0.40d); // #SoSpooky

        this.setOnMouseMoved(event -> {
            if(draggedCable!=null){
                Point2D localPoint = this.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
                draggedCable.update(localPoint);
            }
        });
		ModuleFactory.startSyn();

	}

	public void onRightClick() {dropCable();
	}

    /**
     * Handling event when plug is clicked
     * @param plug
     */
    public void plugClicked(Plug plug){
        if(draggedCable == null){
			Plug opposite = getConnectedPlug(plug);
			if (opposite!=null){
                logger.info("TRY DRAG");
                disconnect(plug);
                draggedCable=getConnectedCable(plug);
                dragCable(draggedCable,plug);

            }else {
                draggedCable = new Cable(this, plug);
                this.getChildren().add(draggedCable);
            }
        }else{
            Plug fixedPlug = draggedCable.getPlug();
            if(fixedPlug != plug) {
                draggedCable.setPlug(plug);
                connect(plug, fixedPlug);
                draggedCable.update();
                draggedCable = null;
            }
            else{
                dropCable();
            }
        }
    }

	public void removeModule(ViewModule module) {
		this.getChildren().remove(module);
	}

	/**
	 * Adds a module to the workbench at the position (0,0)
	 * @param module
     */
	public void addModule(ViewModule module) {
		this.getChildren().add(module);
		makeDraggable(module);
	}

	/**
	 * Ads listeners to a module to make it draggable across the workbench
	 * @param module
	 */
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

			displayGhost(module);
			workbench.getCables().stream().filter(cable -> draggedCable == null).forEach(fr.synthlab.view.component.Cable::front);
        });

        module.setOnMouseReleased(mouseEvent -> {
            hideGhost();
        });

        module.setOnMouseDragged(event -> {
            Point2D localPoint = workbench.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));

            double expectedX = localPoint.getX() - mouseDelta.x;
            double expectedY = localPoint.getY() - mouseDelta.y;
            // Moving the ghost to where the module should be
            workbench.moveGhost(expectedX, expectedY);

            Point2D newLocation = computeNewModulePosition(module, expectedX, expectedY);
            if (newLocation != null) {
                module.relocate(newLocation.getX(), newLocation.getY());
            }
            workbench.getCables().stream().filter(cable -> draggedCable == null).forEach(fr.synthlab.view.component.Cable::update);
        });

	}

	public void displayGhost(ViewModule module){
		module.toFront();
		// Creating a ghost image
		WritableImage snapshot = module.snapshot(new SnapshotParameters(), null);
		dragGhost.setImage(snapshot);
		dragGhost.toFront();
		dragGhost.setMouseTransparent(true);

        // Initial position of the ghost
        Bounds moduleBounds = module.getBoundsInParent();
		this.moveGhost(moduleBounds.getMinX(), moduleBounds.getMinY());
        this.getChildren().add(dragGhost);
	}
	public void hideGhost(){
		this.getChildren().remove(dragGhost);
	}
	public void moveGhost(double x, double y){
		dragGhost.relocate(x, y);
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
				if(child instanceof ViewModule) {
                    Bounds childBounds = child.getBoundsInParent();

                    if (bounds.intersects(childBounds)) {
                        return childBounds;
                    }
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
	public Point2D getBoundsCenter(Bounds bounds) {
		double x, y;
		x = bounds.getMinX() + (bounds.getWidth() / 2.0d);
		y = bounds.getMinY() + (bounds.getHeight() / 2.0d);

		return new Point2D(x, y);
	}

	/**
	 * Try and moves a module to the expected position.
	 * If the position is already occupied by another module or is out out bound,
	 * a ghost will be placed at this location and the module will be move to an
	 * adjacent free position.
	 * @param node The module to move
	 * @param expectedX The desired X coordinate
	 * @param expectedY The desired Y coordinate
	 * @return A suggested location to move the module to
	 */
	public Point2D computeNewModulePosition(ViewModule node, double expectedX, double expectedY) {
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
				return new Point2D(newX, newY);
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
		return null;
	}

    /** Function that call a connection between two port
     * This function first retrieve the port of the two plug in parameter
     *
     * @param in the name is mandatory, we dont care if its in or out
     * @param out the name is mandatory, we dont care if its in or out
     */
    private void connect(Plug in, Plug out){
        Port n1 = in.getPort();
        Port n2 = out.getPort();
        n1.connect(n2);
    }

    /** Function that call a connection between two port
     * This function disconnect a plug from all its relation
     *
     * @param plug the name is mandatory, we dont care if its in or out
     */
    private void disconnect(Plug plug){
        Port p = plug.getPort();
        p.disconnect();
    }
    /** Drop cable based on lastClickedPlug
     *
     */
    private void dropCable(){
        getCables().remove(draggedCable);
        this.getChildren().remove(draggedCable);
        draggedCable=null;
        // TODO Method to remove the cable from the view
        logger.info("Cable dropped");
    }


    /**
     * Returns the list of all currently active cables
     * @return
     */
    private Collection<Cable> getCables() {
        Collection<Cable> cables = new ArrayList<>();
        for (Node child : this.getChildren()) {
            if (child instanceof Cable) {
                cables.add((Cable) child);
            }
        }
        return cables;
    }

    private Cable getConnectedCable(Plug plug){
        Plug test = null;
        for(Cable c : getCables()){
            test = c.getOppositePlug(plug);
            if(test!=null)return c;
        }
        return null;
    }
	private Plug getConnectedPlug(Plug plug){
        Plug opposite = null;
        for(Cable c : getCables()){
            opposite = c.getOppositePlug(plug);
            if(opposite!=null)return opposite;
        }
		return opposite;
	}

    private void dragCable(Cable cable,Plug plug){
        cable.unplug(plug);

    }

}
