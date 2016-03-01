package fr.synthlab.view.component;

import fr.synthlab.view.controller.ToolboxController;
import fr.synthlab.view.controller.Workbench;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by pollt on 2/2/16.
 */

public class Cable extends CubicCurve {
    private static final Logger LOGGER
            = Logger.getLogger(Cable.class.getName());
    private final double circleRadius = 12.0d;
    private Color color;
    private Plug in;
    private Plug out;
    private Circle circleIn;
    private Circle circleOut;
    private Workbench workbench;

    public Cable(final Workbench workbenchInit, final Plug inInit) {
        in = inInit;
        in.setCable(this);
        workbench = workbenchInit;

        init();
    }

    public Cable(final Workbench workbenchInit,
                 final Plug inInit, final Plug outInit) {
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

    public final void updateCircles() {
        circleIn.toFront();
        circleOut.toFront();
    }

    public final void update() {
        Point2D inPosition = workbench.sceneToLocal(
                in.localToScene(in.getCenter()));
        Point2D outPosition = workbench.sceneToLocal(
                out.localToScene(out.getCenter()));

        this.setStartX(inPosition.getX());
        this.setStartY(inPosition.getY());
        this.setEndX(outPosition.getX());
        this.setEndY(outPosition.getY());

        drawCable(inPosition, outPosition);
        addCircle(circleIn, inPosition.getX(), inPosition.getY());
        addCircle(circleOut, outPosition.getX(), outPosition.getY());

        this.toFront();
    }

    public final void update(final Point2D mouse) {
        Point2D correctedMouse = new Point2D(Math.max(
                circleRadius, mouse.getX()),
                Math.max(circleRadius, mouse.getY()));

        in = getPluggedPlug();
        if (out != null) {
            out.setCable(null);
            out = null;
        }
        Point2D inPosition = workbench.sceneToLocal(
                in.localToScene(in.getCenter()));

        this.setStartX(inPosition.getX());
        this.setStartY(inPosition.getY());
        this.setEndX(correctedMouse.getX());
        this.setEndY(correctedMouse.getY());

        drawCable(inPosition, mouse);
        addCircle(circleIn, inPosition.getX(), inPosition.getY());
        addCircle(circleOut, correctedMouse.getX(), correctedMouse.getY());

        this.toFront();
    }

    public final Optional<Plug> getOppositePlug(final Plug plug) {
        if (in == null || out == null) {
            return Optional.empty();
        }
        if (in.equals(plug)) {
            return Optional.of(out);
        } else if (out.equals(plug)) {
            return Optional.of(in);
        } else {
            return Optional.empty();
        }
    }

    public final Plug getPluggedPlug() {
        if (in == null) {
            return out;
        } else {
            return in;
        }
    }

    public final void setEmptyPlug(final Plug plug) {
        if (in == null) {
            in = plug;
            in.setCable(this);
        } else {
            out = plug;
            out.setCable(this);
        }
    }

    private void addCircle(final Circle c, final double x, final double y) {
        c.setCenterX(x);
        c.setCenterY(y);
        c.setRadius(circleRadius);
    }

    public final void allToFront() {
        circleIn.toFront();
        circleOut.toFront();
        this.toFront();
    }

    public final void unplug(final Plug plug) {
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

    public final void deleteCircles() {
        workbench.getChildren().remove(circleIn);
        workbench.getChildren().remove(circleOut);
    }

    private void drawCable(final Point2D start, final Point2D end) {
        double diffX = start.getX() - end.getX();
        double diffY = Math.abs(start.getY() - end.getY());
        this.setControlX1(start.getX() - diffX / 3);
        this.setControlY1(start.getY() + 100 + diffY / 2);
        this.setControlX2(end.getX() + diffX / 3);
        this.setControlY2(end.getY() + 100 + diffY / 2);
        this.setFill(null);
        this.setStroke(color);
    }

    public final void setColor(final Color newColor) {
        color = newColor;
        setStroke(color);
    }

    public final Color getColor() {
        return color;
    }
}
