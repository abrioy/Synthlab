package fr.synthlab.view.component;

import fr.synthlab.model.module.port.Port;
import fr.synthlab.view.controller.ToolboxController;
import fr.synthlab.view.controller.workbench.Workbench;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

import java.util.logging.Logger;

/**
 * Cable.
 */
public class Cable extends CubicCurve {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(Cable.class.getName());
    /**
     * size of end of cable.
     */
    private final double circleRadius = 12.0d;
    /**
     * color.
     */
    private Color color;
    /**
     * plug one.
     */
    private Plug plug1;
    /**
     * plug two.
     */
    private Plug plug2;
    /**
     * circle 1.
     */
    private Circle circle1;
    /**
     * cable 2.
     */
    private Circle circle2;
    /**
     * current workbench.
     */
    private Workbench workbench;

    /**
     * constructor.
     * @param workbenchInit current workbench
     * @param inInit first plug
     */
    public Cable(final Workbench workbenchInit, final Plug inInit) {
        connectPlug(inInit);
        workbench = workbenchInit;
        init();
    }

    /**
     * constructor.
     * @param workbenchInit current workbench
     * @param inInit first plug
     * @param outInit second plug
     */
    public Cable(final Workbench workbenchInit,
                 final Plug inInit, final Plug outInit) {
        connectPlug(inInit);
        connectPlug(outInit);
        workbench = workbenchInit;

        init();
    }

    /**
     * init cable.
     */
    private void init() {
        circle1 = new Circle();
        circle2 = new Circle();
        circle1.setMouseTransparent(true);
        circle2.setMouseTransparent(true);
        circle1.setFill(Color.DARKGRAY);
        circle2.setFill(Color.DARKGRAY);
        circle1.setRadius(circleRadius);
        circle2.setRadius(circleRadius);
        workbench.getChildren().add(circle1);
        workbench.getChildren().add(circle2);
        color = ToolboxController.getColor();

        this.setStrokeWidth(10);
        this.setStrokeLineCap(StrokeLineCap.ROUND);
        this.setMouseTransparent(true);
    }

    /**
     * try connect module by their port.
     * @param port1 first port
     * @param port2 second port
     */
    private void connectPorts(final Port port1, final Port port2) {
        if (port1 != null && port2 != null) {
            port1.connect(port2);
        } else {
            LOGGER.severe("Trying to connect a null port.");
        }
    }

    /**
     * connect plug.
     * @param plug plug to connect
     */
    public final void connectPlug(final Plug plug) {
        if (plug1 == null) {
            plug1 = plug;
            plug1.setCable(this);
            if (plug2 != null) {
                connectPorts(plug1.getPort(), plug2.getPort());
            }
        } else if (plug2 == null) {
            plug2 = plug;
            plug2.setCable(this);
            if (plug1 != null) {
                connectPorts(plug1.getPort(), plug2.getPort());
            }
        } else {
            LOGGER.severe("Trying to connect a cable to more than 2 plugs.");
        }
    }

    /**
     * disconnect plug.
     * @param plug disconnected
     */
    public final void disconnectPlug(final Plug plug) {
        if (plug1 == plug) {
            plug1.setCable(null);
            if (plug1.getPort() != null) {
                plug1.getPort().disconnect();
            }
            plug1 = null;
        } else if (plug2 == plug) {
            plug2.setCable(null);
            if (plug2.getPort() != null) {
                plug2.getPort().disconnect();
            }
            plug2 = null;
        } else {
            LOGGER.severe("Trying to disconnect a cable"
                    + " from a plug it is not connected to.");
        }
    }

    /**
     * @return return plug connected if cable is connected in only one side
     */
    public final Plug getConnectedPlug() {
        if (plug1 != null && plug2 == null) {
            return plug1;
        } else if (plug1 == null && plug2 != null) {
            return plug2;
        } else {
            LOGGER.warning("Trying to get information on a connected"
                    + " plug when there is 0 or 2 connected plugs.");
            return null;
        }
    }

    /**
     * update cable view.
     */
    public final void update() {
        if (plug1 != null && plug2 != null) {
            Point2D position1 = workbench.sceneToLocal(
                    plug1.localToScene(plug1.getCenter()));
            Point2D position2 = workbench.sceneToLocal(
                    plug2.localToScene(plug2.getCenter()));

            drawCable(position1, position2);

            this.toFront();
        } else {
            Plug plug = getConnectedPlug();
            if (plug != null) {
                Point2D plugPosition = workbench.sceneToLocal(
                        plug.localToScene(plug.getCenter()));

                drawCable(plugPosition, plugPosition);
                this.toFront();
            }
        }
    }

    /**
     * update cable view when cable is linked to the mouse.
     * @param mouse mouse linked
     */
    public final void update(final Point2D mouse) {
        Point2D correctedMouse = new Point2D(Math.max(
                circleRadius, mouse.getX()),
                Math.max(circleRadius, mouse.getY()));

        Plug plug = getConnectedPlug();
        if (plug != null) {
            Point2D plugPosition = workbench.sceneToLocal(
                    plug.localToScene(plug.getCenter()));

            drawCable(plugPosition, correctedMouse);

            circle2.toFront();
            this.toFront();
        }
    }

    /**
     * update end of cable.
     */
    public final void updateCircles() {
        circle1.toFront();
        circle2.toFront();
    }

    /**
     * get plug opposite.
     * @param plug plug know
     * @return the other plug
     */
    public final Plug getOppositePlug(final Plug plug) {
        if (plug1 == null || plug2 == null) {
            return null;
        }
        if (plug1.equals(plug)) {
            return plug2;
        } else if (plug2.equals(plug)) {
            return plug1;
        } else {
            return null;
        }
    }

    /**
     * move cable.
     * @param c circle
     * @param x position
     * @param y position
     */
    private void moveCircle(final Circle c, final double x, final double y) {
        c.setCenterX(x);
        c.setCenterY(y);
    }

    /**
     * disconnect cable.
     */
    public final void dispose() {
        if (plug1 != null) {
            plug1.setCable(null);
            if (plug1.getPort() != null) {
                plug1.getPort().disconnect();
            }
        }
        if (plug2 != null) {
            plug2.setCable(null);
            if (plug2.getPort() != null) {
                plug2.getPort().disconnect();
            }
        }
        workbench.getChildren().remove(circle1);
        workbench.getChildren().remove(circle2);
    }

    /**
     * draw cable.
     * @param start position 1
     * @param end position 2
     */
    private void drawCable(final Point2D start, final Point2D end) {
        this.setStartX(start.getX());
        this.setStartY(start.getY());
        this.setEndX(end.getX());
        this.setEndY(end.getY());

        moveCircle(circle1, start.getX(), start.getY());
        moveCircle(circle2, end.getX(), end.getY());

        double diffX = start.getX() - end.getX();
        double diffY = Math.abs(start.getY() - end.getY());
        this.setControlX1(start.getX() - diffX / 3);
        this.setControlY1(start.getY() + 100 + diffY / 2);
        this.setControlX2(end.getX() + diffX / 3);
        this.setControlY2(end.getY() + 100 + diffY / 2);
        this.setFill(null);
        this.setStroke(color);
    }

    /**
     * setter on color.
     * @param newColor to set
     */
    public final void setColor(final Color newColor) {
        color = newColor;
        setStroke(color);
    }

    /**
     * @return color cable
     */
    public final Color getColor() {
        return color;
    }
}
