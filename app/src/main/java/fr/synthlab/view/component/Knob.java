package fr.synthlab.view.component;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.css.Styleable;
import javafx.event.EventTarget;

/**
 * Created by miow on 3/1/16.
 */
public interface Knob extends EventTarget, Styleable {
	double getSpeed();

	void setSpeed(double v);

	DoubleProperty speedProperty();

	/**
	 * getter on current value.
	 *
	 * @return double value
	 */
	double getValue();

	/**
	 * setter on current value.
	 *
	 * @param v to set
	 */
	void setValue(double v);

	/**
	 * value property.
	 *
	 * @return value property
	 */
	DoubleProperty valueProperty();

	/**
	 * getter on min value.
	 *
	 * @return min value
	 */
	double getMin();

	/**
	 * setter on min.
	 *
	 * @param v value to set
	 */
	void setMin(double v);

	/**
	 * min property.
	 *
	 * @return min property
	 */
	DoubleProperty minProperty();

	/**
	 * getter on max.
	 *
	 * @return max value
	 */
	double getMax();

	/**
	 * setter on max.
	 *
	 * @param v max
	 */
	void setMax(double v);

	/**
	 * getter on max property.
	 *
	 * @return max property
	 */
	DoubleProperty maxProperty();

	/**
	 * getter on diameter.
	 *
	 * @return diameter
	 */
	double getDiameter();

	/**
	 * setter diameter.
	 *
	 * @param v new diameter
	 */
	void setDiameter(double v);

	/**
	 * getter diameter property.
	 *
	 * @return diameter property
	 */
	DoubleProperty diameterProperty();

	/**
	 * setter on scale type.
	 *
	 * @param v type
	 */
	void setScaleType(String v);

	/**
	 * getter on scale type.
	 *
	 * @return type
	 */
	String getScaleType();

	/**
	 * getter on scale type property.
	 *
	 * @return type property
	 */
	StringProperty scaleTypeProperty();

	/**
	 * setter on label.
	 *
	 * @param v label
	 */
	void setLabel(String v);

	/**
	 * getter on label.
	 *
	 * @return label
	 */
	String getLabel();

	/**
	 * getter on label property.
	 *
	 * @return label property
	 */
	StringProperty labelProperty();

	/**
	 * setter on number of step.
	 *
	 * @param v number of step
	 */
	void setStep(int v);

	/**
	 * getter on number of step.
	 *
	 * @return number of step
	 */
	int getStep();

	/**
	 * getter on number of step property.
	 *
	 * @return number of step property
	 */
	IntegerProperty stepProperty();

	/**
	 * setter on scale type.
	 *
	 * @param v type
	 */
	void setStepType(boolean v);

	/**
	 * getter on step type.
	 *
	 * @return type
	 */
	boolean getStepType();

	/**
	 * getter on step type property.
	 *
	 * @return type property
	 */
	BooleanProperty stepTypeProperty();

	/**
	 * getter on diameter.
	 *
	 * @return diameter
	 */
	double getMinAngle();

	/**
	 * setter minAngle.
	 *
	 * @param v new minAngle
	 */
	void setMinAngle(double v);

	/**
	 * getter minAngle property.
	 *
	 * @return minAngle property
	 */
	DoubleProperty minAngleProperty();

	/**
	 * getter on diameter.
	 *
	 * @return diameter
	 */
	double getMaxAngle();

	/**
	 * setter maxAngle.
	 *
	 * @param v new maxAngle
	 */
	void setMaxAngle(double v);

	/**
	 * getter maxAngle property.
	 *
	 * @return maxAngle property
	 */
	DoubleProperty maxAngleProperty();
}
