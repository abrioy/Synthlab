package fr.synthlab.view.component;

import javafx.beans.property.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

/**
 * Knob view.
 * @author johan
 * @see Region
 */
public class Knob extends Pane {

    /**
     * say if we can turn button.
     */
    private boolean movable = false;

    /**
     * draw zone.
     */
    private final Region knob;

    /**
     * angle where is the min.
     */
    private final double minAngle = -20;

    /**
     * angle where is the max.
     */
    private final double maxAngle = 200;

    /**
     * size of scale.
     */
    private int scaleSize = 20;

    /**
     * button rotation.
     */
    private final Rotate rotate = new Rotate();

    /**
     * scale max.
     */
    private final Line minLine = new Line();

    /**
     * scale min.
     */
    private final Line maxLine = new Line();

    /**
     * current value of button position.
     */
    private final DoubleProperty value = new SimpleDoubleProperty(this, "value", 0);

    /**
     * value of min position.
     */
    private final DoubleProperty min = new SimpleDoubleProperty(this, "min", 0);

    /**
     * value of max position.
     */
    private final DoubleProperty max = new SimpleDoubleProperty(this, "max", 100);

    /**
     * diameter of button
     */
    private final DoubleProperty diameter = new SimpleDoubleProperty(this, "diameter", 200);

    /**
     * type of button
     */
    private final StringProperty scaleType = new SimpleStringProperty(this, "scaleType", "linear");

    /**
     * name of button
     */
    private final StringProperty label = new SimpleStringProperty(this, "label", "");

    /**
     * nb of step in button.
     * if = 0 it is a button continue.
     */
    private final IntegerProperty step = new SimpleIntegerProperty(this, "step", 0);

    /**
     * constructor.
     */
    public Knob() {
        super();
        knob = new Region();
        knob.getStylesheets().add(
                getClass().getResource("/gui/fxml/style/Knob.css").toExternalForm());//add css
        knob.setPrefSize(diameter.doubleValue(), diameter.doubleValue());
        knob.getStyleClass().add("knob");
        knob.getTransforms().add(rotate);

        /**
         * de/block button to rotate on click
         */
        setOnMouseClicked(event -> movable = !movable);
        /**
         * on mouse exit block button
         */
        setOnMouseExited(event -> movable = false);

        /**
         * rotate button on mouse moving
         */
        setOnMouseMoved(event -> {
            if (movable) {
                double x = event.getX();
                double y = event.getY();
                double centerX = 0;
                double centerY = 0;
                double theta = Math.atan2((y - centerY), (x - centerX));
                double angle = Math.toDegrees(theta);
                if (angle > 0.0) {
                    angle = 180 + (180 - angle);
                } else {
                    angle = 180 - (180 - Math.abs(angle));
                }
                if (angle >= 270) {
                    angle = angle - 360;
                }
                double angleLocal;
                double angleInterval = ((maxAngle - minAngle) / (step.get()-1));
                if (step.get()!=0){//go to step if there are
                    double angleLocalNext = minAngle;
                    for (int t = 0; t < step.get()-1; t++) {
                        angleLocal = angleLocalNext;
                        angleLocalNext = (angleInterval*(t+1) + minAngle);
                        if (angleLocal<angle && angle<((angleLocalNext-angleLocal)/2)+angleLocal) {
                            rotate.setAngle(-angleLocal);
                        }
                        else if (((angleLocalNext-angleLocal)/2)+angleLocal<angle && angle<angleLocalNext){
                            rotate.setAngle(-angleLocalNext);
                        }
                    }
                }
                double value1 = angleToValue(angle);
                setValue(value1);
            }
        });
        minLine.setStroke(Color.GREEN);
        maxLine.setStroke(Color.BLUE);
        getChildren().addAll(minLine, maxLine);
        getChildren().add(knob);
        setPrefSize(diameter.doubleValue() / 5, diameter.doubleValue() / 5);
        valueProperty().addListener((arg0, arg1, arg2) -> {
            requestLayout();
        });
        minProperty().addListener((arg0, arg1, arg2) -> {
            requestLayout();
        });
        maxProperty().addListener((arg0, arg1, arg2) -> {
            requestLayout();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        double centerX = 0;
        double centerY = 0;
        minLine.setStartX(centerX);
        minLine.setStartY(centerY);
        minLine.setEndX(centerX + (diameter.doubleValue() / 2.0 + scaleSize) * Math.cos(Math.toRadians(-minAngle)));
        minLine.setEndY(centerY + (diameter.doubleValue() / 2.0 + scaleSize) * Math.sin(Math.toRadians(-minAngle)));
        maxLine.setStartX(centerX);
        maxLine.setStartY(centerY);
        maxLine.setEndX(centerX + (diameter.doubleValue() / 2.0 + scaleSize) * Math.cos(Math.toRadians(-maxAngle)));
        maxLine.setEndY(centerY + (diameter.doubleValue() / 2.0 + scaleSize) * Math.sin(Math.toRadians(-maxAngle)));
        double knobX = 0 - getDiameter() / 2.0;
        double knobY = 0 - getDiameter() / 2.0;
        knob.setLayoutX(knobX);
        knob.setLayoutY(knobY);
        double angle = valueToAngle(getValue());
        double angleLocal;
        double angleInterval = ((maxAngle - minAngle) / (step.get()-1));
        if (minAngle <= angle && angle <= maxAngle) {
            rotate.setPivotX(knob.getWidth() / 2.0);
            rotate.setPivotY(knob.getHeight() / 2.0);
            rotate.setAngle(-angle);
        }
        if (step.get()!=0) {//draw scale
            Line line;
            for (int x = 1; x < step.get()-1; x++) {
                angleLocal = -(angleInterval*x + minAngle);
                line = new Line();
                line.setStroke(Color.ANTIQUEWHITE);
                line.setStartX(centerX + (diameter.doubleValue() / 2.0) * Math.cos(Math.toRadians(angleLocal)));
                line.setStartY(centerY + (diameter.doubleValue() / 2.0) * Math.sin(Math.toRadians(angleLocal)));
                line.setEndX(centerX + (diameter.doubleValue() / 2.0 + scaleSize) * Math.cos(Math.toRadians(angleLocal)));
                line.setEndY(centerY + (diameter.doubleValue() / 2.0 + scaleSize) * Math.sin(Math.toRadians(angleLocal)));
                getChildren().add(line);
            }
        }
    }

    /**
     * return angle for value
     * @param value value to transform in angle
     * @return angle in degree
     */
    private double valueToAngle(double value) {
        double maxValue = getMax();
        double minValue = getMin();
        return minAngle + (maxAngle - minAngle) * (value - minValue) / (maxValue - minValue);
    }

    /**
     * return value of angle
     * @param angle in degree
     * @return value including log, linear and step.
     */
    private double angleToValue(double angle) {
        double maxValue = getMax();
        double minValue = getMin();
        double value;
        if (step.get()==0) {
            if (scaleType.get().equals("log")) {
                //TODO make value log
                value = minValue + (maxValue - minValue) * (angle - minAngle) / (maxAngle - minAngle);
            } else {
                value = minValue + (maxValue - minValue) * (angle - minAngle) / (maxAngle - minAngle);
            }
            value = Math.max(minValue, value);
            return Math.min(maxValue, value);
        }
        else {//if step
            return (angle / (maxAngle-minAngle)) * minValue / maxValue;
        }
    }

    /**
     * getter on current value
     * @return double value
     */
    public final double getValue() {
        return value.get();
    }

    /**
     * setter on current value
     * @param v to set
     */
    public final void setValue(double v) {
        value.set(v);
    }

    /**
     * value property
     * @return value property
     */
    public final DoubleProperty valueProperty() {
        return value;
    }

    /**
     * getter on min value
     * @return min value
     */
    public final double getMin() {
        return min.get();
    }

    /**
     * setter on min.
     * @param v value to set
     */
    public final void setMin(double v) {
        min.set(v);
    }

    /**
     * min property.
     * @return min property
     */
    public final DoubleProperty minProperty() {
        return min;
    }

    /**
     * getter on max
     * @return max value
     */
    public final double getMax() {
        return max.get();
    }

    /**
     * setter on max
     * @param v max
     */
    public final void setMax(double v) {
        max.set(v);
    }

    /**
     * getter on max property
     * @return max property
     */
    public final DoubleProperty maxProperty() {
        return max;
    }

    /**
     * getter on diameter.
     * @return diameter
     */
    public final double getDiameter() {
        return diameter.get();
    }

    /**
     * setter diameter
     * @param v new diameter
     */
    public final void setDiameter(double v) {
        diameter.set(v);
        knob.setPrefSize(diameter.doubleValue(), diameter.doubleValue());
        scaleSize = (int) (diameter.get() / 5);
    }

    /**
     * getter diameter property
     * @return diameter property
     */
    public final DoubleProperty diameterProperty() {
        return diameter;
    }

    /**
     * setter on scale type
     * @param v type
     */
    public final void setScaleType(String v) {
        if (!v.equals("log")){
            v="linear";
        }
        scaleType.set(v);
    }

    /**
     * getter on scale type.
     * @return type
     */
    public final String getScaleType() {
        return scaleType.get();
    }

    /**
     * getter on scale type property
     * @return type property
     */
    public final StringProperty scaleTypeProperty() {
        return scaleType;
    }

    /**
     * setter on label
     * @param v label
     */
    public final void setLabel(String v) {
        label.set(v);
    }

    /**
     * getter on label
     * @return label
     */
    public final String getLabel() {
        if (label.get().equals("")){
            return scaleType.toString();
        }
        return label.get();
    }

    /**
     * getter on label property
     * @return label property
     */
    public final StringProperty labelProperty() {
        return label;
    }

    /**
     * setter on number of step
     * @param v number of step
     */
    public final void setStep(int v) {
        if (v<0){
            v=0;
        }
        step.set(v);
    }

    /**
     * getter on number of step.
     * @return number of step
     */
    public final int getStep() {
        return step.get();
    }

    /**
     * getter on number of step property.
     * @return number of step property
     */
    public final IntegerProperty stepProperty() {
        return step;
    }
}
