package fr.synthlab.view.controller.workbench;


import fr.synthlab.model.module.ModuleFactory;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Workbench extends Pane {
    private static final Logger LOGGER
            = Logger.getLogger(Workbench.class.getName());

    private final double moduleMargin = 2.0d;
    private ImageView dragGhost = new ImageView();

	private CableManager cableManager;



    public Workbench() {
        // Making the ghost a bit spookier
        dragGhost.setOpacity(0.40d); // #SoSpooky

		cableManager = new CableManager(this);

        this.setOnMouseMoved(event -> {
            if (cableManager.draggedCable != null) {
                Point2D localPoint = this.sceneToLocal(
                        new Point2D(event.getSceneX(), event.getSceneY()));
				cableManager.draggedCable.update(localPoint);
            }
        });

		this.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.SECONDARY) {
				cableManager.dropCable();
				event.consume();
			}
		});

        ModuleFactory.startSyn();
    }


    public final void removeAllModules() {
        getViewModules().forEach(this::removeModule);
    }

    /**
     * Removes a module and all its connections for the workbench.
     *
     * @param module Module to be deleted
     */
    public final void removeModule(final ViewModule module) {
        module.getChildren().stream()
                .filter(child -> child instanceof Pane)
                .forEach(child -> {
            Pane core = (Pane) child;
            core.getChildren().stream()
                    .filter(plug -> plug instanceof Plug)
					.forEach(plug -> {
						Cable c = ((Plug) plug).getCable();

						if (c != null) {
							cableManager.removeCable(c);
						}
					});
				});
		this.getChildren().remove(module);
    }


    /**
     * Called when a module wants to be closed.
     *
     * @param module Module to be closed
     */
    public final void onModuleCloseRequest(final ViewModule module) {
        removeModule(module);
    }


    /**
     * Adds a module to the workbench at the position (0,0).
     *
     * @param module Module to be added
     */
    public final void addModule(final ViewModule module) {
        this.getChildren().add(module);
        makeDraggable(module);
    }

    /**
     * Ads listeners to a module to make it draggable across the workbench.
     *
     * @param module Module to be dragged
     */
    private void makeDraggable(final ViewModule module) {
        final Workbench workbench = this;

        class Delta {
            private double x, y;
        }
        final Delta mouseDelta = new Delta();

        module.setOnMousePressed(event -> {
            Point2D mousePoint
                    = sceneToLocal(
                    new Point2D(event.getSceneX(), event.getSceneY()));
            Point2D localPoint
                    = module.sceneToLocal(
                    new Point2D(event.getSceneX(), event.getSceneY()));
            mouseDelta.x = localPoint.getX();
            mouseDelta.y = localPoint.getY();

			cableManager.updateCables();
            if (cableManager.draggedCable != null) {
				cableManager.draggedCable.update(mousePoint);
            }

            workbench.displayGhost(module);
        });

        module.setOnMouseReleased(mouseEvent -> hideGhost());

        module.setOnMouseDragged(event -> {
            Point2D localPoint
                    = workbench.sceneToLocal(
                    new Point2D(event.getSceneX(), event.getSceneY()));

            double expectedX = localPoint.getX() - mouseDelta.x;
            double expectedY = localPoint.getY() - mouseDelta.y;

            Point2D newLocation
                    = computeNewModulePosition(module, expectedX, expectedY);
            if (newLocation != null) {
                module.relocate(newLocation.getX(), newLocation.getY());
            }

			cableManager.updateCables();
            if (cableManager.draggedCable != null) {
				cableManager.draggedCable.update(localPoint);
            }
            dragGhost.toFront();
        });

    }

    public final void displayGhost(final ViewModule module) {
        if (this.getChildren().contains(dragGhost)) {
            this.getChildren().remove(dragGhost);
            LOGGER.warning("The ghost was added again"
                    + " before it was properly removed.");
        }

        // Creating a ghost image
        WritableImage snapshot
                = module.snapshot(new SnapshotParameters(), null);
        dragGhost.setImage(snapshot);
        dragGhost.toFront();
        dragGhost.setMouseTransparent(true);

        // Initial position of the ghost
        Bounds moduleBounds = module.getBoundsInParent();
        this.moveGhost(moduleBounds.getMinX(), moduleBounds.getMinY());
        this.getChildren().add(dragGhost);
    }

    public final void hideGhost() {
        this.getChildren().remove(dragGhost);
    }

    public final void moveGhost(final double x, final double y) {
        dragGhost.relocate(
                Math.max(moduleMargin, x),
                Math.max(moduleMargin, y)
        );
        dragGhost.toFront();
    }

    public Collection<ViewModule> getViewModules() {
        return this.getChildren().stream()
                .filter(child -> child instanceof ViewModule)
                .map(child -> (ViewModule) child).collect(
                        Collectors.toCollection(ArrayList::new));
    }

	/**
	 * Returns the list of all currently active cables.
	 *
	 * @return list of cable
	 */
	protected Collection<Cable> getCables() {
		return this.getChildren().stream()
				.filter(child -> child instanceof Cable)
				.map(child -> (Cable) child).collect(
						Collectors.toCollection(ArrayList::new));
	}

    /**
     * Checks if a Bounds is colliding with any
     * module except the one provided in the parameter.
     *
     * @param node   The module to exclude for collision checking
     * @param bounds The bounds to check
     * @return The Bounds of the first colliding module if there any
     */
    private Bounds checkCollisions(final Node node, final Bounds bounds) {
        for (ViewModule child : this.getViewModules()) {
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
     * Computes the 2D center of a Bounds object.
     *
     * @param bounds we need to find center
     * @return The center of the rectangle
     */
    private Point2D getBoundsCenter(final Bounds bounds) {
        double x, y;
        x = bounds.getMinX() + (bounds.getWidth() / 2.0d);
        y = bounds.getMinY() + (bounds.getHeight() / 2.0d);
        return new Point2D(x, y);
    }


    /**
     * Try and moves a module to the expected position.
     * If the position is already occupied by another module*
     * or is out out bound,
     * a ghost will be placed at this location and the module will be move to an
     * adjacent free position.
     *
     * @param node      The module to move
     * @param expectedX The desired X coordinate
     * @param expectedY The desired Y coordinate
     * @return A suggested location to move the module to
     */
    public final Point2D computeNewModulePosition(
            final ViewModule node,
            final double expectedX,
            final double expectedY) {
        // Moving the ghost to where the module should be
        this.moveGhost(expectedX, expectedY);

        double newX = expectedX;
        double newY = expectedY;

        // We will try 4 times to find a place for the node
        for (int i = 0; i < 4; i++) {
            newX = Math.max(moduleMargin, newX);
            newY = Math.max(moduleMargin, newY);

            Bounds oldBounds = node.getBoundsInParent();
            Bounds newBounds = new BoundingBox(
                    newX,
                    newY,
                    oldBounds.getWidth(),
                    oldBounds.getHeight()
            );

            Bounds collidingBounds = checkCollisions(node, newBounds);
            if (collidingBounds == null) {
                getCables().forEach(Cable::updateCircles);
                // Updating the cable positions, just in case
                getCables().stream().filter(c -> cableManager.draggedCable != c)
                        .forEach(Cable::update);

                // The new position is not colliding with something
                // We move the node
                return new Point2D(newX, newY);
            } else {
                // Snapping to the colliding bounds

                // Finding out where to put back the node
                Point2D newCenter = getBoundsCenter(newBounds);
                Point2D collidingNodeCenter = getBoundsCenter(collidingBounds);

                double distanceX = Math.abs(
                        newCenter.getX() - collidingNodeCenter.getX());
                double distanceY = Math.abs(
                        newCenter.getY() - collidingNodeCenter.getY());

                if ((distanceX / distanceY)
                        > (collidingBounds.getWidth()
                        / collidingBounds.getHeight())) {
                    // We need to push it along the X axis
                    if (newCenter.getX() > collidingNodeCenter.getX()) {
                        // Right
                        newX = collidingBounds.getMaxX() + moduleMargin;
                    } else {
                        // Left
                        newX = collidingBounds.getMinX()
                                - newBounds.getWidth() - moduleMargin;
                    }

                    // We also snap it in place vertically
                    if (Math.abs(
                            collidingBounds.getMinY() - newBounds.getMinY())
                            < 20) {
                        newY = collidingBounds.getMinY();
                    } else if (Math.abs(
                            collidingBounds.getMaxY() - newBounds.getMaxY())
                            < 20) {
                        newY = collidingBounds.getMaxY()
                                - newBounds.getHeight();
                    }

                } else {
                    // We need to push it along the Y axis
                    if (newCenter.getY() > collidingNodeCenter.getY()) {
                        // Bottom
                        newY = collidingBounds.getMaxY() + moduleMargin;
                    } else {
                        // Top
                        newY = collidingBounds.getMinY()
                                - newBounds.getHeight() - moduleMargin;
                    }
                    // We also snap it in place horizontally
                    if (Math.abs(
                            collidingBounds.getMinX() - newBounds.getMinX())
                            < 20) {
                        newX = collidingBounds.getMinX();
                    } else if (Math.abs(
                            collidingBounds.getMaxX() - newBounds.getMaxX())
                            < 20) {
                        newX = collidingBounds.getMaxX() - newBounds.getWidth();
                    }
                }
            }
        }
        // The loop didn't succeed in finding a non-colliding location,
        // we don't move the node
        return null;
    }


	public void plugClicked(Plug plug) {
		cableManager.plugClicked(plug);
	}

}
