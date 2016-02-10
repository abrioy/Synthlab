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

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class Workbench extends Pane {
	private static final Logger logger = Logger.getLogger(Workbench.class.getName());

	private ImageView dragGhost = new ImageView();
    private Cable draggedCable;

	private final double moduleMargin = 2.0d;

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

	public void onRightClick() {
		dropCable();
	}

	/**
	 * Handling event when plug is clicked
	 * @param plug
	 */
	public void plugClicked(Plug plug){
        String str;
        if (draggedCable==null){str="null";}
        else str="full";
        if(draggedCable == null){
			Plug opposite = getConnectedPlug(plug);
			if (opposite!=null){
                disconnect(plug);
                draggedCable=getConnectedCable(plug);
                dragCable(draggedCable,plug);

            }else {
                draggedCable = new Cable(this, plug);
                this.getChildren().add(draggedCable);
            }
        }else{
            if(getConnectedCable(plug)==null) {
                Plug fixedPlug = draggedCable.getPlug();
                if (fixedPlug != plug) {
                    draggedCable.setPlug(plug);
                    connect(plug, fixedPlug);
                    draggedCable.update();
                    draggedCable = null;
                } else {
                    dropCable();
                }
            }

        }
    }

	/**
	 * Removes a module and all its connections for the workbench
	 * @param module
	 */
	public void removeModule(ViewModule module) {
        for (Node child : module.getChildren()) {
            if (child instanceof Pane) {
                Pane core = (Pane)child;
                for (Node plug : core.getChildren()) {
                    if (plug instanceof Plug) {
                        Cable c =getConnectedCable((Plug)plug);
                        if (c!=null) {
                            disconnect((Plug) plug);
                            c.deleteCircles();
                            getCables().remove(c);
                            this.getChildren().remove(c);
                        }

                    }
                }

            }
        }
		this.getChildren().remove(module);
	}


	/**
	 * Called when a module wants to be closed
	 * @param module
	 */
	public void onModuleCloseRequest(ViewModule module){
		removeModule(module);
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
            Point2D mousePoint = this.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
            Point2D localPoint = module.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
			mouseDelta.x = localPoint.getX();
			mouseDelta.y = localPoint.getY();

			displayGhost(module);
			//workbench.getCables().stream().filter(cable -> draggedCable == null).forEach(fr.synthlab.view.component.Cable::front);
			for(Cable c: getCables()){
				if (draggedCable!=c){
					c.update();
				}
				else{
					c.update(mousePoint);
				}
			}
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
			//workbench.getCables().stream().filter(cable -> draggedCable == null).forEach(fr.synthlab.view.component.Cable::update);
			for (Cable c : getCables()) {
				if (draggedCable != c) {
					c.update();
				} else {
					c.update(localPoint);
				}
			}
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
		dragGhost.relocate(
				Math.max(moduleMargin, x),
				Math.max(moduleMargin, y)
		);
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
	private Point2D getBoundsCenter(Bounds bounds) {
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

		// We will try 4 times to find a place for the node
		for (int i = 0; i < 4; i++) {
			if(newX < moduleMargin){
				newX = moduleMargin;
			}
			if(newY < moduleMargin){
				newY = moduleMargin;
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
						newX = collidingBounds.getMaxX() + moduleMargin;
					} else {
						// Left
						newX = collidingBounds.getMinX() - newBounds.getWidth() - moduleMargin;
					}

					// We also snap it in place vertically
					if(Math.abs(collidingBounds.getMinY() - newBounds.getMinY()) < 25){
						newY = collidingBounds.getMinY();
					}
					else if(Math.abs(collidingBounds.getMaxY() - newBounds.getMaxY()) < 25){
						newY = collidingBounds.getMaxY() - newBounds.getHeight();
					}

				} else {
					// We need to push it along the Y axis
					if (newCenter.getY() > collidingNodeCenter.getY()) {
						// Bottom
						newY = collidingBounds.getMaxY() + moduleMargin;
					} else {
						// Top
						newY = collidingBounds.getMinY() - newBounds.getHeight() - moduleMargin;
					}

					// We also snap it in place horizontally
					if(Math.abs(collidingBounds.getMinX() - newBounds.getMinX()) < 25){
						newX = collidingBounds.getMinX();
					}
					else if(Math.abs(collidingBounds.getMaxX() - newBounds.getMaxX()) < 25){
						newX = collidingBounds.getMaxX() - newBounds.getWidth();
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
        for(Cable c : getCables()){
			Plug test = c.getOppositePlug(plug);
            if(test != null){
				return c;
			}
        }
        return null;
    }
	private Plug getConnectedPlug(Plug plug){
        for(Cable c : getCables()){
			Plug opposite = c.getOppositePlug(plug);
            if(opposite != null){
				return opposite;
			}
        }
		return null;
	}

    private void dragCable(Cable cable,Plug plug){
        cable.unplug(plug);

    }

    /** Drop cable based on lastClickedPlug
     *
     */
    private void dropCable(){
		if(draggedCable!=null) {
			draggedCable.deleteCircles();
			getCables().remove(draggedCable);
			this.getChildren().remove(draggedCable);
			draggedCable = null;
		}
    }
}
