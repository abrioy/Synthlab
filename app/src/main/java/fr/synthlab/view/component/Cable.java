package fr.synthlab.view.component;

import fr.synthlab.view.Workbench;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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

    public Cable(Workbench workbench, Plug pIn, Plug pOut) {
        Bounds pInBounds = pIn.getBoundsInLocal();
        Bounds pOutBounds = pOut.getBoundsInLocal();
        Bounds pInPosition = workbench.sceneToLocal(pIn.localToScene(pInBounds));
        Bounds pOutPosition = workbench.sceneToLocal(pOut.localToScene(pOutBounds));
        in = new Point2D(pInPosition.getMinX(), pInPosition.getMinY());
        out = new Point2D(pOutPosition.getMinY(), pOutPosition.getMinY());
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
