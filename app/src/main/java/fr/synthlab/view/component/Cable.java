package fr.synthlab.view.component;

import fr.synthlab.view.Workbench;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

import java.util.logging.Logger;

/**
 * Created by pollt on 2/2/16.
 */

public class Cable extends Line {
    private static final Logger logger = Logger.getLogger(Cable.class.getName());

    private Plug in;
    private Plug out;

    private Circle circleIn;
    private Circle circleOut;

    private Workbench workbench;

    public Cable(Workbench workbench, Plug in) {
        this.in = in;
        this.workbench = workbench;

        circleIn = new Circle();
        circleOut = new Circle();
        circleIn.setMouseTransparent(true);
        circleOut.setMouseTransparent(true);
        circleIn.setFill(Color.DARKGRAY);
        circleOut.setFill(Color.DARKGRAY);
        workbench.getChildren().add(circleIn);
        workbench.getChildren().add(circleOut);

        this.setStrokeWidth(10);
        this.setStrokeLineCap(StrokeLineCap.ROUND);
        this.setMouseTransparent(true);

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


        addCircle(circleIn, inPosition.getX(), inPosition.getY());
        addCircle(circleOut, outPosition.getX(), outPosition.getY());
        this.setFill(Color.RED);

        this.toFront();
    }

    public void update(Point2D mouse){
        //in=getPlug();
        //out=null;
        Bounds inBounds = getPlug().getBoundsInLocal();
        Point2D inPosition = workbench.getBoundsCenter(workbench.sceneToLocal(getPlug().localToScene(inBounds)));

        this.setStartX(inPosition.getX());
        this.setStartY(inPosition.getY());
        this.setEndX(mouse.getX());
        this.setEndY(mouse.getY());

        addCircle(circleIn, inPosition.getX(), inPosition.getY());
        addCircle(circleOut,mouse.getX(),mouse.getY());
        this.setFill(Color.RED);

        this.toFront();
    }

    public Plug getOppositePlug(Plug plug){
        if(in==null || out==null)return null;
        if(in.equals(plug)){
            return out;
        } else if (out.equals(plug)){
            return in;
        } else {
            return null;
        }
    }

    public void setPlug(Plug plug){
        if(in == null){
            in = plug;
        } else {
            out = plug;
        }
    }

    public Plug getPlug(){
        if(in == null){
            return out;
        } else {
            return in;
        }
    }

    private void addCircle(Circle c, double x, double y){
        c.setCenterX(x);
        c.setCenterY(y);
        c.setRadius(12);
        c.toFront();
    }

    public void front(){
        circleIn.toFront();
        circleOut.toFront();
        this.toFront();
    }

    public void unplug(Plug plug){
        if(in==plug)in=null;
        else out=null;
    }
    public void deleteCircles(){
        workbench.getChildren().remove(circleIn);
        workbench.getChildren().remove(circleOut);

    }
}
