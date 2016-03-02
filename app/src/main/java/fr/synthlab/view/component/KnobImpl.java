package fr.synthlab.view.component;

import javafx.beans.property.*;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Knob view.
 * @author johan
 * @see Region
 */
public class KnobImpl extends Pane implements Knob {
    private static final Logger LOGGER = Logger.getLogger(KnobImpl.class.getName());

    private static final Color STEP_COLOR = Color.WHITE;

    /**
     * draw zone.
     */
    private final Region knobturn;

    /**
     * draw another zone.
     */
    private final Region knobBody;

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
     * arc for graduation.
     */
    private final Arc arc = new Arc();

    private double lastAngularPosition = 80.0d;
    private DoubleProperty speed
            = new SimpleDoubleProperty(this, "slow", Double.MAX_VALUE);

    /**
     * angle where is the min.
     */
    private final DoubleProperty minAngle
            = new SimpleDoubleProperty(this, "minAngle", 240);

    /**
     * angle where is the max.
     */
    private final DoubleProperty maxAngle
            = new SimpleDoubleProperty(this, "maxAngle", -60);

    /**
     * current value of button position.
     */
    private final DoubleProperty value
            = new SimpleDoubleProperty(this, "value", 0);

    /**
     * value of min position.
     */
    private final DoubleProperty min = new SimpleDoubleProperty(this, "min", 0);

    /**
     * value of max position.
     */
    private final DoubleProperty max
            = new SimpleDoubleProperty(this, "max", 100);

    /**
     * diameter of button.
     */
    private final DoubleProperty diameter
            = new SimpleDoubleProperty(this, "diameter", 200);

    /**
     * type of button.
     */
    private final StringProperty scaleType
            = new SimpleStringProperty(this, "scaleType", "linear");

    /**
     * Snapping to step of not.
     */
    private final BooleanProperty stepType
            = new SimpleBooleanProperty(this, "stepType", false);

    /**
     * name of button.
     */
    private final StringProperty label
            = new SimpleStringProperty(this, "label", "");

    /**
     * nb of step in button.
     * if = 0 it is a button continue.
     */
    private final IntegerProperty step
            = new SimpleIntegerProperty(this, "step", 20);

    /**
     * min with exponential.
     */
    private double minExp = Math.log(min.get());

    /**
     * maximum with exponential.
     */
    private double maxExp = Math.log(max.get());

    /**
     * scale to exponential.
     */
    private double scale = (maxExp - minExp) / max.get() - min.get();

    private Collection<Line> lines = new ArrayList<>();

    /**
     * coef to exponential.
     */
    private double coef = scale * 0 - minExp;

    /**
     * label name.
     */
    private Label name;

    /**
     * constructor.
     */
    public KnobImpl() {
        super();
        knobturn = new Region();
        knobturn.getStylesheets().add(
                getClass().getResource("/gui/fxml/style/Knob.css")
                        .toExternalForm()); //add css
        knobturn.setPrefSize(diameter.doubleValue(), diameter.doubleValue());
        knobturn.getStyleClass().add("knobturn");
        knobturn.getTransforms().add(rotate);

        knobBody = new Region();
        knobBody.getStylesheets().add(
                getClass().getResource("/gui/fxml/style/Knob.css")
                        .toExternalForm()); //add css
        knobBody.setPrefSize(diameter.doubleValue(), diameter.doubleValue());
        knobBody.getStyleClass().add("knobBody");
        knobBody.setMouseTransparent(true);

        setMaxHeight(Double.MIN_VALUE);
        setMaxWidth(Double.MIN_VALUE);

        setOnMousePressed(Event::consume);
        setOnMouseReleased(Event::consume);
        setOnMouseDragged(Event::consume);
        setOnMouseDragReleased(Event::consume);
        setOnMouseDragged(event -> {
            moveKnob(event.getX(), event.getY());
            event.consume();
        });

        minLine.setStroke(Color.WHITE);
        maxLine.setStroke(Color.WHITE);
        getChildren().addAll(minLine, maxLine);
        getChildren().add(knobturn);
        getChildren().add(knobBody);

        arc.setFill(Color.TRANSPARENT);
        arc.setStroke(Color.WHITE);
        getChildren().add(arc);

        name = new Label(getLabel());
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
            knobturn.setPrefSize(newValue.doubleValue(), newValue.doubleValue());
            scaleSize = (int) (diameter.get() / 5);
        });

        // The label has a width and a height of 0 before it is initialized
        // We use these listener to place is in its correct place
        name.widthProperty().addListener((observable, oldValue, newValue) -> {
            name.setLayoutX(-newValue.doubleValue() / 2.0d);
        });
        name.heightProperty().addListener((observable, oldValue, newValue) -> {
            name.setLayoutY(-getDiameter()
                    / 2.0d - newValue.doubleValue() - scaleSize);
        });

        widthProperty().addListener((observable, oldValue, newValue) -> {
            updatePositions();
        });
        heightProperty().addListener((observable, oldValue, newValue) -> {
            updatePositions();
        });

		setOnMouseClicked(event -> {
			if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
				if(!this.scaleType.get().equals("enum")) {
					showPickerPopup();
				}
			}
		});

    }

    private void moveKnob(final double x, final double y) {
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

        double angularStep = (270 / Math.abs(min.getValue() - max.getValue()))
                * speed.getValue();
        if (Math.abs(angle - lastAngularPosition) > angularStep) {
            if (angle - lastAngularPosition > angularStep) {
                angle = lastAngularPosition + angularStep;
            } else {
                angle = lastAngularPosition - angularStep;
            }
            lastAngularPosition = angle;
        }

        double angleLocal;
        double angleInterval = ((getMinAngle() - getMaxAngle())
                / (step.get() - 1));
        if (getStepType()) { //go to step if there are
            double angleLocalNext = getMaxAngle();
            for (int t = 0; t < step.get() - 1; t++) {
                angleLocal = angleLocalNext;
                angleLocalNext = (angleInterval * (t + 1) + getMaxAngle());
                if (angleLocal < angle
                        && angle
                        <= ((angleLocalNext - angleLocal) / 2) + angleLocal) {
                    rotate.setAngle(-angleLocal);
                    angle = angleLocal;
                } else if (((angleLocalNext - angleLocal) / 2) + angleLocal
                        < angle
                        && angle < angleLocalNext) {
                    rotate.setAngle(-angleLocalNext);
                    angle = angleLocalNext;
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
    protected final void layoutChildren() {
        double stepStart;
        double stepEnd;
        double smallScaleSize = scaleSize * 3.0d / 4.0d;
        double arcDistance = scaleSize * 0.4d;
        double arcRadius = (diameter.doubleValue() / 2.0) + arcDistance;

        double minStartX =
                (diameter.doubleValue() / 2.0 + arcDistance)
                        * Math.cos(Math.toRadians(-getMinAngle()));
        double minStartY =
                (diameter.doubleValue() / 2.0 + arcDistance)
                        * Math.sin(Math.toRadians(-getMinAngle()));
        double maxStartX =
                (diameter.doubleValue() / 2.0 + arcDistance)
                        * Math.cos(Math.toRadians(-getMaxAngle()));
        double maxStartY =
                (diameter.doubleValue() / 2.0 + arcDistance)
                        * Math.sin(Math.toRadians(-getMaxAngle()));

        super.layoutChildren();
        double centerX = 0;
        double centerY = 0;
        minLine.setStartX(minStartX);
        minLine.setStartY(minStartY);
        minLine.setEndX(
                centerX + (diameter.doubleValue() / 2.0 + scaleSize)
                        * Math.cos(Math.toRadians(-getMinAngle())));
        minLine.setEndY(
                centerY + (diameter.doubleValue() / 2.0 + scaleSize)
                        * Math.sin(Math.toRadians(-getMinAngle())));
        maxLine.setStartX(maxStartX);
        maxLine.setStartY(maxStartY);
        maxLine.setEndX(
                centerX + (diameter.doubleValue() / 2.0 + scaleSize)
                        * Math.cos(Math.toRadians(-getMaxAngle())));
        maxLine.setEndY(
                centerY + (diameter.doubleValue() / 2.0 + scaleSize)
                        * Math.sin(Math.toRadians(-getMaxAngle())));
        double knobX = 0 - getDiameter() / 2.0;
        double knobY = 0 - getDiameter() / 2.0;
        knobturn.setLayoutX(knobX);
        knobturn.setLayoutY(knobY);
        knobBody.setLayoutX(knobX);
        knobBody.setLayoutY(knobY);
        double angle = valueToAngle(getValue());
        double angleLocal;
        double angleInterval =
                ((getMaxAngle() - getMinAngle()) / (step.get() - 1));
        if (getMinAngle() >= angle && angle >= getMaxAngle()) {
            rotate.setPivotX(knobturn.getWidth() / 2.0);
            rotate.setPivotY(knobturn.getHeight() / 2.0);
            rotate.setAngle(-angle);
        }
        if (step.get() != 0) { //draw scale
            getChildren().removeAll(lines);
            Color interColor = STEP_COLOR;
            lines.clear();
            //draw different line depending on the scaleType
            if (getScaleType().equals("enum")) {
                setMaxAngle(100);
                setMinAngle(-20);
                stepStart = arcDistance;
                stepEnd = scaleSize;
                interColor = STEP_COLOR;
            } else {
                interColor = interColor.darker();
                stepStart = smallScaleSize / 3;
                stepEnd = smallScaleSize;
            }
            for (int x = 1; x < step.get() - 1; x++) {
                angleLocal = -(angleInterval * x + getMinAngle());
                Line line = new Line();
                line.setStroke(interColor);
                line.setStartX(
                        centerX + (diameter.doubleValue() / 2.0 + stepStart)
                        * Math.cos(Math.toRadians(angleLocal)));
                line.setStartY(
                        centerY + (diameter.doubleValue() / 2.0 + stepStart)
                        * Math.sin(Math.toRadians(angleLocal)));
                line.setEndX(centerX + (diameter.doubleValue() / 2.0 + stepEnd)
                        * Math.cos(Math.toRadians(angleLocal)));
                line.setEndY(centerY + (diameter.doubleValue() / 2.0 + stepEnd)
                        * Math.sin(Math.toRadians(angleLocal)));
                lines.add(line);
                getChildren().add(line);
            }
        }
        arc.setCenterX(centerX);
        arc.setCenterY(centerY);
        arc.setRadiusX(arcRadius);
        arc.setRadiusY(arcRadius);
        arc.setStartAngle(getMinAngle());
        arc.setLength((getMaxAngle() - getMinAngle()));
        arc.setType(ArcType.OPEN);
        arc.setMouseTransparent(true);
        if (getScaleType().equals("enum")) {
            arc.setStroke(Color.TRANSPARENT);
        }
        updatePositions();
    }

    /**
     * return angle for valueOfAngle.
     *
     * @param valueOfAngle valueOfAngle to transform in angle
     * @return angle in degree
     */
    private double valueToAngle(final double valueOfAngle) {
        double maxValue = getMax();
        double minValue = getMin();
        if (scaleType.get().equals("log")) {
            return getMinAngle() + (getMaxAngle() - getMinAngle())
                    * (((Math.log(valueOfAngle) + coef) / scale) - minValue)
                    / (maxValue - minValue);
        }
        return getMinAngle() + (getMaxAngle() - getMinAngle())
                * (valueOfAngle - minValue) / (maxValue - minValue);
    }

    /**
     * return value of angle.
     *
     * @param angle in degree
     * @return value including log, linear and step.
     */
    private double angleToValue(final double angle) {
        double maxValue = getMax();
        double minValue = getMin();
        double valueOfAngle;
        if (scaleType.get().equals("log")) {
            valueOfAngle = minValue + (maxValue - minValue)
                    * (angle - getMinAngle()) / (getMaxAngle() - getMinAngle());
            valueOfAngle = Math.exp(minExp + scale * (valueOfAngle - minValue));
            valueOfAngle = Math.max(
                    (minValue <= 10.0 ? 10 : minValue), valueOfAngle);
        } else {
            valueOfAngle = minValue + (maxValue - minValue)
                    * (angle - getMinAngle()) / (getMaxAngle() - getMinAngle());
        }
        valueOfAngle = Math.max(minValue, valueOfAngle);
        return Math.min(maxValue, valueOfAngle);
    }

    private void updatePositions() {
        for (Node node : getChildren()) {
            node.translateXProperty().set(getWidth() / 2.0d);
            node.translateYProperty().set(getHeight() / 2.0d);
        }
    }

	public void showPickerPopup(){
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Adjust "+label.get());
		dialog.setHeaderText("Modify the value of this property");
		dialog.setResizable(true);
		dialog.getEditor().setText(String.valueOf(this.getValue()));

		Optional<String> res = dialog.showAndWait();
		if(res.isPresent()){
			try {
				this.setValue(Double.parseDouble(res.get()));
			}
			catch (NumberFormatException e){
				LOGGER.warning("Enable to parse \""+res.get()+"\" into a number.");
			}
		}
	}

    @Override
	public final double getSpeed() {
        return speed.get();
    }

    @Override
	public final void setSpeed(final double v) {
        speed.set(v);
    }

    @Override
	public final DoubleProperty speedProperty() {
        return speed;
    }


    @Override
	public final double getValue() {
        return value.get();
    }

    @Override
	public final void setValue(final double v) {
		double newValue = Math.max(getMin(), v);
		newValue = Math.min(getMax(), newValue);
		LOGGER.info(String.valueOf(getMin()));
		LOGGER.info(String.valueOf(v));
        value.set(newValue);
    }

    @Override
	public final DoubleProperty valueProperty() {
        return value;
    }

    @Override
	public final double getMin() {
        return min.get();
    }

    @Override
	public final void setMin(final double v) {
        double v2 = (v <= 10.0) ? 10 : v;
        minExp = Math.log(v2);
        scale = (maxExp - minExp) / max.get() - min.get();
        coef = (scale * 0) - minExp;
        min.set(v2);
    }

    @Override
	public final DoubleProperty minProperty() {
        return min;
    }

    @Override
	public final double getMax() {
        return max.get();
    }

    @Override
	public final void setMax(final double v) {
        max.set(v);
        maxExp = Math.log(v);
        scale = (maxExp - minExp) / max.get() - min.get();
        coef = (scale * 0) - minExp;
    }

    @Override
	public final DoubleProperty maxProperty() {
        return max;
    }

    @Override
	public final double getDiameter() {
        return diameter.get();
    }

    @Override
	public final void setDiameter(final double v) {
        diameter.set(v);
        knobturn.setPrefSize(diameter.doubleValue(), diameter.doubleValue());
        knobBody.setPrefSize(diameter.doubleValue(), diameter.doubleValue());

        scaleSize = (int) (diameter.get() / 5);
    }

    @Override
	public final DoubleProperty diameterProperty() {
        return diameter;
    }

    @Override
	public final void setScaleType(String v) {
        if (!v.equals("log") && !v.equals("enum")) {
            v = "linear";
        }
        scaleType.set(v);
    }

    @Override
	public final String getScaleType() {
        return scaleType.get();
    }

    @Override
	public final StringProperty scaleTypeProperty() {
        return scaleType;
    }

    @Override
	public final void setLabel(final String v) {
        label.set(v);
        name.setText(v);
    }

    @Override
	public final String getLabel() {
        return label.get();
    }

    @Override
	public final StringProperty labelProperty() {
        return label;
    }

    @Override
	public final void setStep(int v) {
        if (v < 0) {
            v = 0;
        }
        step.set(v);
    }

    @Override
	public final int getStep() {
        return step.get();
    }

    @Override
	public final IntegerProperty stepProperty() {
        return step;
    }

    @Override
	public final void setStepType(final boolean v) {
        stepType.set(v);
    }

    @Override
	public final boolean getStepType() {
        return stepType.get();
    }

    @Override
	public final BooleanProperty stepTypeProperty() {
        return stepType;
    }

    @Override
	public final double getMinAngle() {
        return minAngle.get();
    }

    @Override
	public final void setMinAngle(final double v) {
        if (getScaleType().equals("enum")) {
            minAngle.set(225);
        } else {
            minAngle.set(v);
        }
    }

    @Override
	public final DoubleProperty minAngleProperty() {
        return minAngle;
    }

    @Override
	public final double getMaxAngle() {
        return maxAngle.get();
    }

    @Override
	public final void setMaxAngle(final double v) {
        if (getScaleType().equals("enum")) {
            maxAngle.set(-45);
        } else {
            maxAngle.set(v);
        }
    }

    @Override
	public final DoubleProperty maxAngleProperty() {
        return maxAngle;
    }


}
