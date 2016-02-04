package fr.synthlab.view.component;

import javafx.beans.property.*;
import javafx.geometry.Insets;
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
    private boolean movable = false;
    private Region knob;

    private final double minAngle = -20;
    private final double maxAngle = 200;
    private int scaleSize = 20;
    private Rotate rotate = new Rotate();
    private Line minLine = new Line();
    private Line maxLine = new Line();

    private final DoubleProperty value = new SimpleDoubleProperty(this, "value", 0);
    private final DoubleProperty min = new SimpleDoubleProperty(this, "min", 0);
    private final DoubleProperty max = new SimpleDoubleProperty(this, "max", 100);
    private final DoubleProperty diameter = new SimpleDoubleProperty(this, "diameter", 200);
    private final StringProperty scaleType = new SimpleStringProperty(this, "scaleType", "linear");
    private final StringProperty label = new SimpleStringProperty(this, "label", "");
    private final IntegerProperty step = new SimpleIntegerProperty(this, "step", 0);

    public Knob() {
        super();
        knob = new Region();
        knob.getStylesheets().add(
                getClass().getResource("/gui/fxml/style/Knob.css").toExternalForm());
        knob.setPrefSize(diameter.doubleValue(), diameter.doubleValue());
        knob.getStyleClass().add("knob");
        knob.getTransforms().add(rotate);
        setOnMouseClicked(event -> movable = !movable);
        setOnMouseExited(event -> movable = false);
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
                    System.out.println("echo");
                } else {
                    angle = 180 - (180 - Math.abs(angle));
                    System.out.println("not");
                }
                if (angle >= 270) {
                    angle = angle - 360;
                    System.out.println("lol");
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
        else if (step.get()!=0){
            double angleLocalNext = -(angleInterval*(1) + minAngle);
            for (int x = 1; x < step.get()-1; x++) {
                angleLocal = angleLocalNext;
                angleLocalNext = -(angleInterval*(x+1) + minAngle);

            }
        }
        if (step.get()!=0) {
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

    double valueToAngle(double value) {
        double maxValue = getMax();
        double minValue = getMin();
        return minAngle + (maxAngle - minAngle) * (value - minValue) / (maxValue - minValue);
    }

    double angleToValue(double angle) {
        double maxValue = getMax();
        double minValue = getMin();
        double value;
        if (scaleType.get().equals("log")){
            //TODO make value log
            value = minValue + (maxValue - minValue) * (angle - minAngle) / (maxAngle - minAngle);
        }
        else {
            value = minValue + (maxValue - minValue) * (angle - minAngle) / (maxAngle - minAngle);
        }
        value = Math.max(minValue, value);
        value = Math.min(maxValue, value);
        return value;
    }

    public final double getValue() {
        return value.get();
    }

    public final void setValue(double v) {
        value.set(v);
    }

    public final DoubleProperty valueProperty() {
        return value;
    }

    public final double getMin() {
        return min.get();
    }

    public final void setMin(double v) {
        min.set(v);
    }

    public final DoubleProperty minProperty() {
        return min;
    }

    public final double getMax() {
        return max.get();
    }

    public final void setMax(double v) {
        max.set(v);
    }

    public final DoubleProperty maxProperty() {
        return max;
    }

    public final double getDiameter() {
        return diameter.get();
    }

    public final void setDiameter(double v) {
        diameter.set(v);
        knob.setPrefSize(diameter.doubleValue(), diameter.doubleValue());
        scaleSize = (int) (diameter.get() / 5);
    }

    public final DoubleProperty diameterProperty() {
        return diameter;
    }

    public final void setScaleType(String v) {
        if (!v.equals("log")){
            v="linear";
        }
        scaleType.set(v);
    }

    public final String getScaleType() {
        return scaleType.get();
    }

    public final StringProperty scaleTypeProperty() {
        return scaleType;
    }

    public final void setLabel(String v) {
        label.set(v);
    }

    public final String getLabel() {
        if (label.get().equals("")){
            return scaleType.toString();
        }
        return label.get();
    }

    public final StringProperty labelProperty() {
        return label;
    }
    public final void setStep(int v) {
        step.set(v);
    }

    public final int getStep() {
        return step.get();
    }

    public final IntegerProperty stepProperty() {
        return step;
    }
}
