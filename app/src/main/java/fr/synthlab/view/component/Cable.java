package fr.synthlab.view.component;

import fr.synthlab.view.Workbench;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.logging.Logger;

/**
 * Created by pollt on 2/2/16.
 */

public class Cable extends Line {
    private static final Logger logger = Logger.getLogger(Cable.class.getName());

    private Plug in;
    private Plug out;

    private Workbench workbench;

    public Cable(Workbench workbench, Plug in) {
        this.in = in;
        this.workbench = workbench;

        this.setFill(Color.BLACK);
        this.setStrokeWidth(20);
        this.setMouseTransparent(true);
    }

    public Cable(Workbench workbench, Plug in, Plug out) {
        this.in = in;
        this.out = out;
        this.workbench = workbench;

        this.setFill(Color.BLACK);
        this.setStrokeWidth(20);
        this.setMouseTransparent(true);

        update();
    }

    public void update(){
        Bounds inBounds = in.getBoundsInLocal();
        Bounds outBounds = out.getBoundsInLocal();
        Point2D inPosition = workbench.getBoundsCenter(workbench.sceneToLocal(in.localToScene(inBounds)));
        Point2D outPosition = workbench.getBoundsCenter(workbench.sceneToLocal(out.localToScene(outBounds)));


        this.setStartX(inPosition.getX());
        this.setStartY(inPosition.getY());
        this.setEndX(outPosition.getX());
        this.setEndY(outPosition.getY());

        this.toFront();
    }

    public void update(Point2D mouse){
        Bounds inBounds = in.getBoundsInLocal();
        Point2D inPosition = workbench.getBoundsCenter(workbench.sceneToLocal(in.localToScene(inBounds)));

        this.setStartX(inPosition.getX());
        this.setStartY(inPosition.getY());
        this.setEndX(mouse.getX());
        this.setEndY(mouse.getY());

        this.toFront();
    }

    public Plug getOppositePlug(Plug plug){
        if(in.equals(plug)){
            return out;
        } else if (out.equals(plug)){
            return in;
        } else {
            return null;
        }
    }

}
