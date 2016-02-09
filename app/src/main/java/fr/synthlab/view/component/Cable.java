package fr.synthlab.view.component;

import fr.synthlab.view.Workbench;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by pollt on 2/2/16.
 */

public class Cable extends CubicCurve {
    private static final Logger logger = Logger.getLogger(Cable.class.getName());
    private Color color;
    private List<Color> colors;
    private Plug in;
    private Plug out;

    private Circle circleIn;
    private Circle circleOut;

    private Workbench workbench;

    public Cable(Workbench workbench, Plug in) {
        List<Color> colors= new ArrayList<>();
        colors.add(Color.DARKBLUE);
        colors.add(Color.DARKGREEN);
        colors.add(Color.DARKGOLDENROD);
        colors.add(Color.DARKRED);
        colors.add(Color.DARKTURQUOISE);

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
        color=getRandomColor(colors);
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

        drawCable(inPosition, outPosition);
        addCircle(circleIn, inPosition.getX(), inPosition.getY());
        addCircle(circleOut, outPosition.getX(), outPosition.getY());

        this.toFront();
    }

    public void update(Point2D mouse){
        Bounds inBounds = getPlug().getBoundsInLocal();
        Point2D inPosition = workbench.getBoundsCenter(workbench.sceneToLocal(getPlug().localToScene(inBounds)));

        this.setStartX(inPosition.getX());
        this.setStartY(inPosition.getY());
        this.setEndX(mouse.getX());
        this.setEndY(mouse.getY());

        drawCable(inPosition,mouse);
        addCircle(circleIn, inPosition.getX(), inPosition.getY());
        addCircle(circleOut,mouse.getX(),mouse.getY());


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
    private void drawCable(Point2D start, Point2D end){
        double diffX=start.getX() - end.getX();
        double diffY=Math.abs(start.getY() - end.getY());
        this.setControlX1(start.getX() - diffX / 3);
        this.setControlY1(start.getY()+100+diffY/2);
        this.setControlX2(end.getX() + diffX / 3);
        this.setControlY2(end.getY() + 100 + diffY / 2);
        this.setFill(null);
        this.setStroke(color);
    }
    private Color getRandomColor(List<Color> colors){
        Random rnd = new Random();
        int i = rnd.nextInt(colors.size());
        return (Color) colors.toArray()[i];
    }
    public void changeRandColor(){
        color=getRandomColor(colors);
    }
}
