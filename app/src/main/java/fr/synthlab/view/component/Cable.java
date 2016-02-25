package fr.synthlab.view.component;

import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.controller.ToolboxController;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

import java.util.logging.Logger;

/**
 * Created by pollt on 2/2/16.
 */

public class Cable extends CubicCurve {
    private static final Logger LOGGER = Logger.getLogger(Cable.class.getName());
    private final double circleRadius = 12.0d;
    private Color color;
    private Plug in;
    private Plug out;
    private Circle circleIn;
    private Circle circleOut;
    private Workbench workbench;

    public Cable(Workbench workbenchInit, Plug inInit) {
        in = inInit;
        in.setCable(this);
        workbench = workbenchInit;

        init();
    }

    public Cable(Workbench workbenchInit, Plug inInit, Plug outInit) {
        in = inInit;
        in.setCable(this);
        out = outInit;
        out.setCable(this);
        workbench = workbenchInit;

        init();
    }

    private void init() {
        circleIn = new Circle();
        circleOut = new Circle();
        circleIn.setMouseTransparent(true);
        circleOut.setMouseTransparent(true);
        circleIn.setFill(Color.DARKGRAY);
        circleOut.setFill(Color.DARKGRAY);
        workbench.getChildren().add(circleIn);
        workbench.getChildren().add(circleOut);
        color = ToolboxController.getColor();

        this.setStrokeWidth(10);
        this.setStrokeLineCap(StrokeLineCap.ROUND);
        this.setMouseTransparent(true);
    }

    public void updateCircles() {
        circleIn.toFront();
        circleOut.toFront();
    }

    public void update() {
        Point2D inPosition = workbench.sceneToLocal(in.localToScene(in.getCenter()));
        Point2D outPosition = workbench.sceneToLocal(out.localToScene(out.getCenter()));

        this.setStartX(inPosition.getX());
        this.setStartY(inPosition.getY());
        this.setEndX(outPosition.getX());
        this.setEndY(outPosition.getY());

        drawCable(inPosition, outPosition);
        addCircle(circleIn, inPosition.getX(), inPosition.getY());
        addCircle(circleOut, outPosition.getX(), outPosition.getY());

        this.toFront();
    }

    public void update(Point2D mouse) {
        Point2D correctedMouse = new Point2D(Math.max(circleRadius, mouse.getX()),
                Math.max(circleRadius, mouse.getY()));

        in = getPluggedPlug();
        if (out != null) {
            out.setCable(null);
            out = null;
        }
        Point2D inPosition = workbench.sceneToLocal(in.localToScene(in.getCenter()));

        this.setStartX(inPosition.getX());
        this.setStartY(inPosition.getY());
        this.setEndX(correctedMouse.getX());
        this.setEndY(correctedMouse.getY());

        drawCable(inPosition, mouse);
        addCircle(circleIn, inPosition.getX(), inPosition.getY());
        addCircle(circleOut, correctedMouse.getX(), correctedMouse.getY());

        this.toFront();
    }

    public Plug getOppositePlug(Plug plug) {
        if (in == null || out == null) {
            return null;
        }
        if (in.equals(plug)) {
            return out;
        } else if (out.equals(plug)) {
            return in;
        } else {
            return null;
        }
    }

    public Plug getPluggedPlug() {
        if (in == null) {
            return out;
        } else {
            return in;
        }
    }

    public void setEmptyPlug(Plug plug) {
        if (in == null) {
            in = plug;
            in.setCable(this);
        } else {
            out = plug;
            out.setCable(this);
        }
    }

    private void addCircle(Circle c, double x, double y) {
        c.setCenterX(x);
        c.setCenterY(y);
        c.setRadius(circleRadius);
    }

    public void allToFront() {
        circleIn.toFront();
        circleOut.toFront();
        this.toFront();
    }

    public void unplug(Plug plug) {
        if (in == plug) {
            in.setCable(null);
            in = null;
        } else {
            if (out != null) {
                out.setCable(null);
                out = null;
            }
        }
    }

    public void deleteCircles() {
        workbench.getChildren().remove(circleIn);
        workbench.getChildren().remove(circleOut);
    }

    private void drawCable(Point2D start, Point2D end) {
        double diffX = start.getX() - end.getX();
        double diffY = Math.abs(start.getY() - end.getY());
        this.setControlX1(start.getX() - diffX / 3);
        this.setControlY1(start.getY() + 100 + diffY / 2);
        this.setControlX2(end.getX() + diffX / 3);
        this.setControlY2(end.getY() + 100 + diffY / 2);
        this.setFill(null);
        this.setStroke(color);
    }

    public void setColor(Color newColor) {
        color = newColor;
        setStroke(color);
    }

    public Color getColor() {
        return color;
    }
}
