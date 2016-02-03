package fr.synthlab.view.component;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.text.MessageFormat;

/**
 * Knob view.
 * @see Region
 * @author johan
 */
public class Knob extends Region {

    private boolean movable = false;
    private Region knob;// = RegionBuilder.create().id("knob").build(); // NOI18N.
    private final double minAngle = -20;
    private final double maxAngle = 200;
    private int scaleSize = 20;
    private Rotate rotate = new Rotate();
    private Line minLine = new Line();
    private Line maxLine = new Line();
    private Text text = new Text();

    private final DoubleProperty value = new SimpleDoubleProperty(this, "value", 0);
    private final DoubleProperty min = new SimpleDoubleProperty(this, "min", 0);
    private final DoubleProperty max = new SimpleDoubleProperty(this, "max", 100);
    private final DoubleProperty diameter = new SimpleDoubleProperty(this, "diameter", 200);


    public Knob() {
        super();
        knob = new Region();
        knob.setPrefSize(diameter.doubleValue(), diameter.doubleValue());
        knob.getStyleClass().add("knob");
        knob.getTransforms().add(rotate);
        setOnMouseClicked(event -> movable = !movable);
        setOnMouseExited(event -> movable = false);
        setOnMouseMoved(event -> {
            if (movable) {
                double x = event.getX();
                double y = event.getY();
                double centerX = getWidth() / 2.0;
                double centerY = getHeight() / 2.0;
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
                double value1 = angleToValue(angle);
                text.setText(MessageFormat.format("{0}\n{1}", angle, value1));
                setValue(value1);
            }
        });
        minLine.setStroke(Color.GREEN);
        maxLine.setStroke(Color.BLUE);
        text.setTextOrigin(VPos.TOP);
        getChildren().addAll(minLine, maxLine);
        getChildren().add(knob);
        getChildren().addAll(text);
        setPrefSize(diameter.doubleValue(), diameter.doubleValue());
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
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;
        minLine.setStartX(centerX);
        minLine.setStartY(centerY);
        minLine.setEndX(centerX + (diameter.doubleValue() / 2.0 + scaleSize) * Math.cos(Math.toRadians(-minAngle)));
        minLine.setEndY(centerY + (diameter.doubleValue() / 2.0 + scaleSize) * Math.sin(Math.toRadians(-minAngle)));
        maxLine.setStartX(centerX);
        maxLine.setStartY(centerY);
        maxLine.setEndX(centerX + (diameter.doubleValue() / 2.0 + scaleSize) * Math.cos(Math.toRadians(-maxAngle)));
        maxLine.setEndY(centerY + (diameter.doubleValue() / 2.0 + scaleSize) * Math.sin(Math.toRadians(-maxAngle)));
        double knobX = (getWidth() - knob.getPrefWidth()) / 2.0;
        double knobY = (getHeight() - knob.getPrefHeight()) / 2.0;
        knob.setLayoutX(knobX);
        knob.setLayoutY(knobY);
        double angle = valueToAngle(getValue());
        if (minAngle <= angle && angle <= maxAngle) {
            rotate.setPivotX(knob.getWidth() / 2.0);
            rotate.setPivotY(knob.getHeight() / 2.0);
            rotate.setAngle(-angle);
        }
    }

    double valueToAngle(double value) {
        double maxValue = getMax();
        double minValue = getMin();
        return minAngle + (maxAngle - minAngle) * (value - minValue) / (maxValue - minValue);
    }

    double angleToValue(double angle) {
        double maxValue = getMax();
        double minValue = getMin();
        double value = minValue + (maxValue - minValue) * (angle - minAngle) / (maxAngle - minAngle);
        value = Math.max(minValue, value);
        value = Math.min(maxValue, value);
        System.out.println(value);
        return value;
    }

    public final void setValue(double v) {
        value.set(v);
    }

    public final double getValue() {
        return value.get();
    }

    public final DoubleProperty valueProperty() {
        return value;
    }


    public final void setMin(double v) {
        min.set(v);
    }

    public final double getMin() {
        return min.get();
    }

    public final DoubleProperty minProperty() {
        return min;
    }

    public final void setMax(double v) {
        max.set(v);
    }

    public final double getMax() {
        return max.get();
    }

    public final DoubleProperty maxProperty() {
        return max;
    }
    public final void setDiameter(double v) {
        diameter.set(v);
        knob.setPrefSize(diameter.doubleValue(), diameter.doubleValue());
        scaleSize= (int) (diameter.get() / 5);
    }

    public final double getDiameter() {
        return diameter.get();
    }

    public final DoubleProperty diameterProperty() {
        return diameter;
    }
}
