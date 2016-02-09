package fr.synthlab.view.component;

import javafx.beans.property.*;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Knob view.
 * @author johan
 * @see Region
 */
public class Knob extends Pane {

    /**
     * draw zone.
     */
    private final Region knob;

    /**
     * angle where is the min.
     */
    private final double minAngle = 200;

    /**
     * angle where is the max.
     */
    private final double maxAngle = -20;

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
     * min with exponential
     */
    private double minExp = Math.log(min.get());

    /**
     * maximum with exponential
     */
    private double maxExp = Math.log(max.get());

    /**
     * scale to exponential.
     */
    private double scale= (maxExp - minExp) / max.get() - min.get();

	private Collection<Line> lines = new ArrayList<>();

    /**
     * coef to exponential
     */
    private double coef = scale * 0 - minExp ;

    /**
     * label name.
     */
    private Label name;

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

		setOnMousePressed(Event::consume);
		setOnMouseReleased(Event::consume);
		setOnMouseDragged(Event::consume);
		setOnMouseDragReleased(Event::consume);
        setOnMouseDragged(event -> {
			moveKnob(event.getX(), event.getY());
			event.consume();
        });

        minLine.setStroke(Color.DARKGRAY);
        maxLine.setStroke(Color.DARKRED);
        getChildren().addAll(minLine, maxLine);
        getChildren().add(knob);
        setPrefSize(diameter.doubleValue() / 5, diameter.doubleValue() / 5);

        name = new Label(getLabel());
        name.setLayoutX(getDiameter()/2);
        name.setLayoutY(getDiameter()/2);
        getChildren().add(name);

        valueProperty().addListener((arg0, arg1, arg2) -> {
            requestLayout();
        });
        minProperty().addListener((arg0, arg1, arg2) -> {
            requestLayout();
        });
        maxProperty().addListener((arg0, arg1, arg2) -> {
            requestLayout();
        });

		diameter.addListener((observable, oldValue, newValue) -> {
			knob.setPrefSize(newValue.doubleValue(), newValue.doubleValue());
			scaleSize = (int) (diameter.get() / 5);
			name.setLayoutX(-5*getLabel().length()/2);
			name.setLayoutY(-getDiameter()/2-25);
		});

		widthProperty().addListener((observable, oldValue, newValue) -> {
			updatePositions();
		});
		heightProperty().addListener((observable, oldValue, newValue) -> {
			updatePositions();
		});
    }

	private void moveKnob(double x, double y) {
		double centerX = getWidth() / 2.0d;
		double centerY = getHeight() / 2.0d;
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
		double angleInterval = ((minAngle - maxAngle ) / (step.get()-1));
		if (step.get()!=0){//go to step if there are
			double angleLocalNext = maxAngle;
			for (int t = 0; t < step.get()-1; t++) {
				angleLocal = angleLocalNext;
				angleLocalNext = (angleInterval*(t+1) + maxAngle);
				if (angleLocal<angle && angle<((angleLocalNext-angleLocal)/2)+angleLocal) {
					rotate.setAngle(-angleLocal);
					angle=angleLocal;
				}
				else if (((angleLocalNext-angleLocal)/2)+angleLocal<angle && angle<angleLocalNext){
					rotate.setAngle(-angleLocalNext);
					angle=angleLocalNext;
				}
			}
		}
		double value1 = angleToValue(angle);
		setValue(value1);
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
        if (minAngle >= angle && angle >= maxAngle) {
            rotate.setPivotX(knob.getWidth() / 2.0);
            rotate.setPivotY(knob.getHeight() / 2.0);
            rotate.setAngle(-angle);
        }
        if (step.get()!=0) {//draw scale
			getChildren().removeAll(lines);
			lines.clear();
            for (int x = 1; x < step.get()-1; x++) {
                angleLocal = -(angleInterval*x + minAngle);
                Line line = new Line();
                line.setStroke(Color.ANTIQUEWHITE);
                line.setStartX(centerX + (diameter.doubleValue() / 2.0) * Math.cos(Math.toRadians(angleLocal)));
                line.setStartY(centerY + (diameter.doubleValue() / 2.0) * Math.sin(Math.toRadians(angleLocal)));
                line.setEndX(centerX + (diameter.doubleValue() / 2.0 + scaleSize) * Math.cos(Math.toRadians(angleLocal)));
                line.setEndY(centerY + (diameter.doubleValue() / 2.0 + scaleSize) * Math.sin(Math.toRadians(angleLocal)));
                lines.add(line);
				getChildren().add(line);
            }
        }
		updatePositions();
    }

    /**
     * return angle for value
     * @param value value to transform in angle
     * @return angle in degree
     */
    private double valueToAngle(double value) {
        double maxValue = getMax();
        double minValue = getMin();
        if (scaleType.get().equals("log")){
            return minAngle + (maxAngle - minAngle) * (((Math.log(value) + coef) / scale) - minValue) / (maxValue - minValue);
        }
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
        if (scaleType.get().equals("log")) {
            value = minValue + (maxValue - minValue) * (angle - minAngle) / (maxAngle - minAngle);
            value = Math.exp(minExp + scale*(value-minValue));
            value = Math.max((minValue <= 10.0 ? 10 : minValue),value);
        } else {
            value = minValue + (maxValue - minValue) * (angle - minAngle) / (maxAngle - minAngle);
        }
        value = Math.max(minValue, value);
        return Math.min(maxValue, value);
    }

	private void updatePositions() {
		for(Node node : getChildren()){
			node.translateXProperty().set(getWidth() / 2.0d);
			node.translateYProperty().set(getHeight() / 2.0d);
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
        double v2 = (v <= 10.0) ? 10 : v;
        minExp = Math.log(v2);
        scale = (maxExp - minExp) / max.get() - min.get();
        coef = (scale * 0) - minExp;
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
        maxExp = Math.log(v);
        scale = (maxExp - minExp) / max.get() - min.get();
        coef = (scale * 0) - minExp;
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
        name.setText(v);
    }

    /**
     * getter on label
     * @return label
     */
    public final String getLabel() {
        if (label.get().equals("")){
            return scaleType.get();
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
