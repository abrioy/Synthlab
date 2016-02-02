package fr.synthlab.view.component;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.shape.Shape;

/**
 * Created by pollt on 2/2/16.
 */

public class Cable {
    Line node;

    public void setIn(Point2D in) {
        this.in = in;
    }

    public void setOut(Point2D out) {
        this.out = out;
    }

    private Point2D in;
    private Point2D out;

    public Cable(Line node) {
        this.node = node;
    }

    public Line create() {
        node = new Line(in.getX(), in.getY(), out.getX(), out.getY());
        node.setFill(Color.BLACK);
        node.setStrokeWidth(20);
        return node;
    }

    public void update(){
        node.setStartX(in.getX());
        node.setStartY(in.getY());
        node.setStartX(out.getX());
        node.setStartY(out.getY());
    }


}
