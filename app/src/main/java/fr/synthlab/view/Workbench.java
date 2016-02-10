package fr.synthlab.view;


import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.moduleFactory.ModuleFactory;
import fr.synthlab.model.module.port.Port;
import fr.synthlab.view.component.Cable;
import fr.synthlab.view.component.Plug;
import fr.synthlab.view.module.ViewModule;
import fr.synthlab.view.viewModuleFactory.ViewModuleFactory;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.logging.Logger;

public class Workbench extends Pane {
	private static final Logger logger = Logger.getLogger(Workbench.class.getName());
	private final double moduleMargin = 2.0d;
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

	private void serializeViewModules() throws IOException {

		FileOutputStream file = null;
		try {
			file = new FileOutputStream("testfile");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ObjectOutputStream outputStream = new ObjectOutputStream(file);

		Collection<ViewModule> modules = getViewModules();

		// Writing the total number of modules
		outputStream.writeInt(modules.size());

		for(ViewModule viewModule : modules){
			Module module = viewModule.getModule();

			// Writing module type
			outputStream.writeObject(module.getType());

			// Writing viewModule position
			Bounds viewModuleBounds = viewModule.getBoundsInParent();
			outputStream.writeDouble(viewModuleBounds.getMinX());
			outputStream.writeDouble(viewModuleBounds.getMinY());

			// Writing Module connections
			// TODO

			// Writing Module data
			viewModule.writeObject(outputStream);
		}
		outputStream.close();

	}

	private void deSerializeViewModules() throws IOException {
		removeAllModules();

		FileInputStream file = null;
		try {
			file = new FileInputStream("testfile");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ObjectInputStream inputStream = new ObjectInputStream(file);

		// Reading the number of modules
		int nbModules = inputStream.readInt();

		for(int i = 0; i < nbModules; i++) {
			try {
				// Reading type
				ModuleEnum type = (ModuleEnum) inputStream.readObject();

				// Reading position
				double xPos = inputStream.readDouble();
				double yPos = inputStream.readDouble();

				// Reading connections
				// TODO

				// Creating new ViewModule and feeding it the gathered data
				ViewModule viewModule = ViewModuleFactory.createViewModule(type, this);
				viewModule.relocate(xPos, yPos);

				// Feeding data to the Module
				viewModule.readObject(inputStream);

				this.addModule(viewModule);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		inputStream.close();
	}


	public void onRightClick() {
		dropCable();
	}

	/**
	 * Handling event when plug is clicked
	 * @param plug
	 */
	public void plugClicked(Plug plug){
		Scanner scanner=new Scanner(System.in);
		while (true) {
			String question = scanner.nextLine();
			if(question.equals("a")){
				try {
					this.serializeViewModules();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			if(question.equals("z")){
				try {
					this.deSerializeViewModules();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}

		
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

	private void removeAllModules() {
		getViewModules().forEach(this::removeModule);
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

			workbench.updateCables();
			if (draggedCable != null) {
				draggedCable.update(mousePoint);
			}

			workbench.displayGhost(module);
		});

        module.setOnMouseReleased(mouseEvent -> {
            hideGhost();
        });

        module.setOnMouseDragged(event -> {
			Point2D localPoint = workbench.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));

			double expectedX = localPoint.getX() - mouseDelta.x;
			double expectedY = localPoint.getY() - mouseDelta.y;

			Point2D newLocation = computeNewModulePosition(module, expectedX, expectedY);
			if (newLocation != null) {
				module.relocate(newLocation.getX(), newLocation.getY());
			}

			updateCables();
			if (draggedCable != null) {
				draggedCable.update(localPoint);
			}
			dragGhost.toFront();
		});

	}

	public void displayGhost(ViewModule module){
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
		dragGhost.toFront();
	}

	private Collection<ViewModule> getViewModules() {
		Collection<ViewModule> modules = new ArrayList<>();
		for (Node child : this.getChildren()) {
			if (child instanceof ViewModule) {
				modules.add((ViewModule) child);
			}
		}
		return modules;
	}

	/**
	 * Checks if a Bounds is colliding with any module except the one provided in the parameter
	 *
	 * @param node   The module to exclude for collision checking
	 * @param bounds The bounds to check
	 * @return The Bounds of the first colliding module if there any
	 */
	private Bounds checkCollisions(Node node, Bounds bounds) {
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
				// Updating the cable positions, just in case
				for (Cable c : getCables()) {
					if (draggedCable != c) {
						c.update();
					}
				}

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

				if ((distanceX / distanceY) > (collidingBounds.getWidth() / collidingBounds.getHeight())) {
					// We need to push it along the X axis
					if (newCenter.getX() > collidingNodeCenter.getX()) {
						// Right
						newX = collidingBounds.getMaxX() + moduleMargin;
					} else {
						// Left
						newX = collidingBounds.getMinX() - newBounds.getWidth() - moduleMargin;
					}

					// We also snap it in place vertically
					if(Math.abs(collidingBounds.getMinY() - newBounds.getMinY()) < 20) {
						newY = collidingBounds.getMinY();
					}
					else if(Math.abs(collidingBounds.getMaxY() - newBounds.getMaxY()) < 20) {
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
					if(Math.abs(collidingBounds.getMinX() - newBounds.getMinX()) < 20) {
						newX = collidingBounds.getMinX();
					}
					else if(Math.abs(collidingBounds.getMaxX() - newBounds.getMaxX()) < 20) {
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

	private void updateCables() {
		getCables().forEach(Cable::updateCircles);

		for(Cable c : getCables()){
			if (draggedCable!=c){
				c.update();
			}
		}
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
			this.getChildren().remove(draggedCable);
			draggedCable = null;
		}
    }
}
