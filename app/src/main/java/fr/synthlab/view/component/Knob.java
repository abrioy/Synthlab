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
 * Created by johan on 03/02/16.
 */
public class Knob extends Region {

    private Region knob;// = RegionBuilder.create().id("knob").build(); // NOI18N.
    private final double minAngle = -20;
    private final double maxAngle = 200;
    private Rotate rotate = new Rotate();
    private Line currentLine = new Line();
    private Line minLine = new Line();
    private Line maxLine = new Line();
    private Text text = new Text();

    public Knob() {
        super();
        knob = new Region();
        knob.setPrefSize(75, 75);
        knob.getStyleClass().add("knob");
        knob.getTransforms().add(rotate);
        setOnMouseMoved(event -> {
            double x = event.getX();
            double y = event.getY();
            double centerX = getWidth() / 2.0;
            double centerY = getHeight() / 2.0;
            currentLine.setStartX(centerX);
            currentLine.setStartY(centerY);
            currentLine.setEndX(x);
            currentLine.setEndY(y);
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
        });
        minLine.setStroke(Color.GREEN);
        maxLine.setStroke(Color.BLUE);
        text.setTextOrigin(VPos.TOP);
        getChildren().addAll(minLine, maxLine);
        getChildren().add(knob);
        getChildren().addAll(currentLine, text);
        setPrefSize(100, 100);
        /*valueProperty().addListener((arg0, arg1, arg2) -> {
            requestLayout();
        });
        minProperty().addListener((arg0, arg1, arg2) -> {
            requestLayout();
        });
        maxProperty().addListener((arg0, arg1, arg2) -> {
            requestLayout();
        });*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;
        currentLine.setStartX(centerX);
        currentLine.setStartY(centerY);
        minLine.setStartX(centerX);
        minLine.setStartY(centerY);
        minLine.setEndX(centerX + 90 * Math.cos(Math.toRadians(-minAngle)));
        minLine.setEndY(centerY + 90 * Math.sin(Math.toRadians(-minAngle)));
        maxLine.setStartX(centerX);
        maxLine.setStartY(centerY);
        maxLine.setEndX(centerX + 90 * Math.cos(Math.toRadians(-maxAngle)));
        maxLine.setEndY(centerY + 90 * Math.sin(Math.toRadians(-maxAngle)));
        double knobX = (getWidth() - knob.getPrefWidth()) / 2.0;
        double knobY = (getHeight() - knob.getPrefHeight()) / 2.0;
        knob.setLayoutX(knobX);
        knob.setLayoutY(knobY);
        double value = getValue();
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
        double angle = minAngle + (maxAngle - minAngle) * (value - minValue) / (maxValue - minValue);
        System.out.printf("valueToAngle %f => %f", value, angle).println();
        return angle;
    }

    double angleToValue(double angle) {
        double maxValue = getMax();
        double minValue = getMin();
        double value = minValue + (maxValue - minValue) * (angle - minAngle) / (maxAngle - minAngle);
        value = Math.max(minValue, value);
        value = Math.min(maxValue, value);
        System.out.printf("angleToValue %f => %f", angle, value).println();
        return value;
    }

    //
    private final DoubleProperty value = new SimpleDoubleProperty(this, "value", 0); // NOI18N.

    public final void setValue(double v) {
        value.set(v);
    }

    public final double getValue() {
        return value.get();
    }

    public final DoubleProperty valueProperty() {
        return value;
    }

    private final DoubleProperty min = new SimpleDoubleProperty(this, "min", 0); // NOI18N.

    public final void setMin(double v) {
        min.set(v);
    }

    public final double getMin() {
        return min.get();
    }

    public final DoubleProperty minProperty() {
        return min;
    }

    private final DoubleProperty max = new SimpleDoubleProperty(this, "max", 100); // NOI18N.

    public final void setMax(double v) {
        max.set(v);
    }

    public final double getMax() {
        return max.get();
    }

    public final DoubleProperty maxProperty() {
        return max;
    }
}
