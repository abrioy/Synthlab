package fr.synthlab.view.controller;


import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleFactory;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;
import fr.synthlab.view.Skin;
import fr.synthlab.view.component.Cable;
import fr.synthlab.view.component.Plug;
import fr.synthlab.view.module.ViewModule;
import fr.synthlab.view.module.ViewModuleFactory;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Workbench extends Pane {
    private static final Logger LOGGER = Logger.getLogger(Workbench.class.getName());

    private final double moduleMargin = 2.0d;
    private ImageView dragGhost = new ImageView();

    private Cable draggedCable;

    private Skin currentSkin = Skin.Default;

    public Workbench() {

        // Making the ghost a bit spookier
        dragGhost.setOpacity(0.40d); // #SoSpooky

        this.setOnMouseMoved(event -> {
            if (draggedCable != null) {
                Point2D localPoint = this.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
                draggedCable.update(localPoint);
            }
        });

        ModuleFactory.startSyn();

    }

    private int serializeModuleToUID(Module module) {
        return System.identityHashCode(module);
    }


    public void serializeViewModules(ObjectOutputStream outputStream) throws IOException {
        Collection<ViewModule> modules = getViewModules();

        // Writing the total number of modules
        outputStream.writeInt(modules.size());

        for (ViewModule viewModule : modules) {
            Module module = viewModule.getModule();

            // Writing module type
            ModuleType type = module.getType();
            LOGGER.fine("Saving module of type \"" + type + "\".");
            outputStream.writeObject(type);

            // Writing viewModule position
            Bounds viewModuleBounds = viewModule.getBoundsInParent();
            LOGGER.fine("\tPosition: (" + viewModuleBounds.getMinX() + ", " + viewModuleBounds.getMinY() + ").");
            outputStream.writeDouble(viewModuleBounds.getMinX());
            outputStream.writeDouble(viewModuleBounds.getMinY());

            // Writing ViewModule data
            LOGGER.fine("\tData.");
            viewModule.writeObject(outputStream);

            // Writing ViewModule connections
            // Writing the UID of this module
            int uid = serializeModuleToUID(viewModule.getModule());
            LOGGER.fine("\tId: " + uid);
            outputStream.writeInt(uid);

            // Writing all the ports
            Collection<Plug> plugs = viewModule.getPlugs();
            // Filtering connected ports
            Collection<Plug> connectedPlugs = plugs.stream()
                    .filter(plug -> plug.getPort().isConnected())
                    .collect(Collectors.toList());

            int nbPlugs = connectedPlugs.size();
            outputStream.writeInt(nbPlugs);
            LOGGER.fine("\tConnections (" + nbPlugs + ")");
            for (Plug plug : connectedPlugs) {
                outputStream.writeObject(plug.getName());
                // Storing the ID of the connected module
                Port connectedPort = plug.getPort().getConnected();
                int targetUID = serializeModuleToUID(connectedPort.getModule());
                outputStream.writeInt(targetUID);
                outputStream.writeObject(connectedPort.getName());
                // Storing the color of the connections
                Color cableColor = plug.getCable().getColor();
                outputStream.writeDouble(cableColor.getRed());
                outputStream.writeDouble(cableColor.getGreen());
                outputStream.writeDouble(cableColor.getBlue());
                LOGGER.fine("\t\t" + plug.getName() + "\t-- " + cableColor + "\t--> " + targetUID + "." + connectedPort.getName());
            }
        }
    }

    public void deSerializeViewModules(ObjectInputStream inputStream) throws IOException {
        removeAllModules();

        Map<Integer, ViewModule> moduleList = new HashMap<>();
        class PortReference {
            private int parentUID;
            private String name;
            private int connectedUID;
            private String connectedPortName;
            private Color cableColor;

            public PortReference(int parentUIDInit, String nameInit,
                                 int connectedUIDInit, String connectedPortNameInit, Color cableColorInit) {
                parentUID = parentUIDInit;
                name = nameInit;
                connectedUID = connectedUIDInit;
                connectedPortName = connectedPortNameInit;
                cableColor = cableColorInit;
            }
        }
        Collection<PortReference> portList = new ArrayList<>();

        // Reading the number of modules
        int nbModules = inputStream.readInt();

        for (int i = 0; i < nbModules; i++) {
            try {
                // Reading type
                ModuleType type = (ModuleType) inputStream.readObject();
                LOGGER.fine("Restoring a module of type \"" + type + "\".");

                // Reading position
                double xPos = inputStream.readDouble();
                double yPos = inputStream.readDouble();
                LOGGER.fine("\tPosition: (" + xPos + ", " + yPos + ").");

                // Creating new ViewModule and feeding it the gathered data
                ViewModule viewModule = ViewModuleFactory.createViewModule(type, this);
                viewModule.relocate(xPos, yPos);

                // Feeding data to the Module
                LOGGER.fine("\tData.");
                viewModule.readObject(inputStream);

                // Reading connections
                // Adding this module to the global list
                int moduleUID = inputStream.readInt();
                LOGGER.fine("\tId: " + moduleUID);
                moduleList.put(moduleUID, viewModule);

                // Adding this module's port to the list
                int nbPlugs = inputStream.readInt();
                LOGGER.fine("\tFound " + nbPlugs + " connections");
                for (int j = 0; j < nbPlugs; j++) {
                    String plugName = (String) inputStream.readObject();
                    int connectedModuleId = inputStream.readInt();
                    String connectedPortName = (String) inputStream.readObject();
                    double r = inputStream.readDouble();
                    double g = inputStream.readDouble();
                    double b = inputStream.readDouble();
                    Color cableColor = new Color(r, g, b, 1.0d);
                    portList.add(new PortReference(
                            moduleUID, plugName, connectedModuleId, connectedPortName, cableColor));
                    LOGGER.fine("\t\t" + plugName + "\t-- " + cableColor + "\t--> " + connectedModuleId + "." + connectedPortName);
                }

                this.addModule(viewModule);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Resolving connections
        LOGGER.fine("Restoring " + portList.size() + " connections");
        for (PortReference portReference : portList) {
            ViewModule viewModule = moduleList.get(portReference.parentUID);
            Plug plug = viewModule.getPlugByName(portReference.name);
            ViewModule connectedViewModule = moduleList.get(portReference.connectedUID);
            Plug connectedPlug = connectedViewModule.getPlugByName(portReference.connectedPortName);

            // Checking this port is not already connected
            if (!plug.getPort().isConnected()) {
                LOGGER.fine("\t" + portReference.parentUID + "." + portReference.name
                        + " -- " + portReference.cableColor + " --> "
                        + portReference.connectedUID + "." + portReference.connectedPortName);
                this.connectPlugs(plug, connectedPlug);
                Cable cable = new Cable(this, plug, connectedPlug);
                cable.setColor(portReference.cableColor);
                this.getChildren().add(cable);
            }
        }
        // Updating cable positions
        getCables().forEach(Cable::updateCircles);
        getCables().forEach(Cable::update);

        inputStream.close();
    }


    public void onRightClick() {
        dropCable();
    }


    public void removeAllModules() {
        getViewModules().forEach(this::removeModule);
    }

    /**
     * Removes a module and all its connections for the workbench
     *
     * @param module Module to be deleted
     */
    public void removeModule(ViewModule module) {
        module.getChildren().stream().filter(child -> child instanceof Pane).forEach(child -> {
            Pane core = (Pane) child;
            core.getChildren().stream().filter(plug -> plug instanceof Plug).forEach(plug -> {
                Cable c = getConnectedCable((Plug) plug);
                if (c != null) {
                    disconnectPlug((Plug) plug);
                    c.deleteCircles();
                    this.getChildren().remove(c);
                }

            });

        });
        this.getChildren().remove(module);
    }


    /**
     * Called when a module wants to be closed
     *
     * @param module Module to be closed
     */
    public void onModuleCloseRequest(ViewModule module) {
        removeModule(module);
    }


    /**
     * Adds a module to the workbench at the position (0,0)
     *
     * @param module Module to be added
     */
    public void addModule(ViewModule module) {
        this.getChildren().add(module);
        makeDraggable(module);
    }

    /**
     * Ads listeners to a module to make it draggable across the workbench
     *
     * @param module Module to be dragged
     */
    private void makeDraggable(ViewModule module) {
        final Workbench workbench = this;

        class Delta {
            private double x, y;
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

        module.setOnMouseReleased(mouseEvent -> hideGhost());

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

    public void displayGhost(ViewModule module) {
        if (this.getChildren().contains(dragGhost)) {
            this.getChildren().remove(dragGhost);
            LOGGER.warning("The ghost was added again before it was properly removed.");
        }

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

    public void hideGhost() {
        this.getChildren().remove(dragGhost);
    }

    public void moveGhost(double x, double y) {
        dragGhost.relocate(
                Math.max(moduleMargin, x),
                Math.max(moduleMargin, y)
        );
        dragGhost.toFront();
    }

    private Collection<ViewModule> getViewModules() {
        return this.getChildren().stream().filter(child -> child instanceof ViewModule)
                .map(child -> (ViewModule) child).collect(Collectors.toCollection(ArrayList::new));
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


    /**
     * Try and moves a module to the expected position.
     * If the position is already occupied by another module or is out out bound,
     * a ghost will be placed at this location and the module will be move to an
     * adjacent free position.
     *
     * @param node      The module to move
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
                getCables().forEach(Cable::updateCircles);
                // Updating the cable positions, just in case
                getCables().stream().filter(c -> draggedCable != c).forEach(Cable::update);

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
                    if (Math.abs(collidingBounds.getMinY() - newBounds.getMinY()) < 20) {
                        newY = collidingBounds.getMinY();
                    } else if (Math.abs(collidingBounds.getMaxY() - newBounds.getMaxY()) < 20) {
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
                    if (Math.abs(collidingBounds.getMinX() - newBounds.getMinX()) < 20) {
                        newX = collidingBounds.getMinX();
                    } else if (Math.abs(collidingBounds.getMaxX() - newBounds.getMaxX()) < 20) {
                        newX = collidingBounds.getMaxX() - newBounds.getWidth();
                    }
                }
            }
        }

        // The loop didn't succeed in finding a non-colliding location, we don't move the node
        return null;
    }

    /**
     * Handling event when plug is clicked
     *
     * @param plug Plug clicked
     */
    public void plugClicked(Plug plug) {
        if (draggedCable == null) {
            Plug opposite = getConnectedPlug(plug);
            if (opposite != null) {
                disconnectPlug(plug);
                draggedCable = getConnectedCable(plug);
                dragCable(draggedCable, plug);
            } else {
                draggedCable = new Cable(this, plug);
                this.getChildren().add(draggedCable);
            }
        } else {
            if (getConnectedCable(plug) == null) {
                Plug fixedPlug = draggedCable.getPluggedPlug();
                if (fixedPlug != plug) {
                    draggedCable.setEmptyPlug(plug);
                    connectPlugs(plug, fixedPlug);
                    draggedCable.update();
                    draggedCable = null;
                } else {
                    dropCable();
                }
            }

        }
    }

    /**
     * Function that call a connection between two port
     * This function first retrieve the port of the two plug in parameter
     *
     * @param in  the name is mandatory, we dont care if its in or out
     * @param out the name is mandatory, we dont care if its in or out
     */
    private void connectPlugs(Plug in, Plug out) {
        Port n1 = in.getPort();
        Port n2 = out.getPort();
        n1.connect(n2);
    }

    /**
     * Function that call a connection between two port
     * This function disconnects a plug from all its relation
     *
     * @param plug the name is mandatory, we dont care if its in or out
     */
    private void disconnectPlug(Plug plug) {
        Port p = plug.getPort();
        if (p.isConnected()) {
            p.disconnect();
        }
    }

    /**
     * Returns the list of all currently active cables
     *
     * @return
     */
    private Collection<Cable> getCables() {
        return this.getChildren().stream().filter(child -> child instanceof Cable)
                .map(child -> (Cable) child).collect(Collectors.toCollection(ArrayList::new));
    }

    private void updateCables() {
        getCables().forEach(Cable::updateCircles);

        getCables().forEach(cable -> {
            if (draggedCable != cable) {
                cable.update();
            }
        });
    }

    private Cable getConnectedCable(Plug plug) {
        for (Cable c : getCables()) {
            if (c.getPluggedPlug() == plug) {
                return c;
            }
            if (c.getOppositePlug(plug) != null) {
                return c;
            }
        }
        return null;
    }

    private Plug getConnectedPlug(Plug plug) {
        for (Cable c : getCables()) {
            Plug opposite = c.getOppositePlug(plug);
            if (opposite != null) {
                return opposite;
            }
        }
        return null;
    }

    private void dragCable(Cable cable, Plug plug) {
        cable.unplug(plug);

    }

    /**
     * Drop cable based on lastClickedPlug
     */
    private void dropCable() {
        if (draggedCable != null) {
            draggedCable.deleteCircles();
            this.getChildren().remove(draggedCable);
            draggedCable = null;
        }
    }


    public Skin getCurrentSkin() {
        return currentSkin;
    }

    public void changeSkin(Skin skin) {
        LOGGER.fine("Skin changed from \"" + currentSkin + "\" to \"" + skin + "\".");

        this.getStylesheets().remove(currentSkin.getPath());
        this.getStylesheets().add(skin.getPath());
        this.applyCss();

        for (ViewModule viewModule : getViewModules()) {
            viewModule.getStylesheets().remove(currentSkin.getPath());
            viewModule.getStylesheets().add(skin.getPath());
            viewModule.applyCss();
        }
    }
}
