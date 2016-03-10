package fr.synthlab.view.component;

import fr.synthlab.model.module.port.Port;
import fr.synthlab.view.controller.workbench.Workbench;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * draw plug.
 */
public class Plug extends StackPane {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Plug.class.getName());

    /**
     * color plug.
     */
    private Circle colorCircle;
    /**
     * cable connected to this plug.
     */
    private Cable cable;

    /**
     * current workbench.
     */
    private Workbench workbench;

    /**
     * setter on workbench.
     * @param newWorkbench to set
     */
    public final void setWorkbench(final Workbench newWorkbench) {
        this.workbench = newWorkbench;
    }

    /**
     * Callable port.
     */
    private Callable<Port> getPortCommand = null;

    /**
     * setter on callable port.
     * @param newGetPortCommand to set
     */
    public final void setGetPortCommand(
            final Callable<Port> newGetPortCommand) {
        getPortCommand = newGetPortCommand;
    }

    /**
     * name of plug.
     */
    private Label nameLabel;

    /**
     * type of plug.
     */
    public enum Type {
        /**
         * input plug.
         */
        input(Color.LIGHTBLUE),
        /**
         * output plug.
         */
        output(Color.ORANGE),
        /**
         * modulation plug.
         */
        modulation(Color.HOTPINK),
        /**
         * other plug.
         */
        other(Color.WHITE);

        /**
         * color of plug.
         */
        private Color color;

        /**
         * constructor.
         * @param aColor color
         */
        Type(final Color aColor) {
            color = aColor;
        }

        /**
         * get type by its name.
         * @param name of type
         * @return type of plug
         */
        public static Type getType(final String name) {
            switch (name) {
                case "input":
                    return input;
                case "output":
                    return output;
                case "modulation":
                    return modulation;
                default:
                    return other;
            }
        }
    }

    /**
     * property on label.
     */
    private final StringProperty label
            = new SimpleStringProperty(this, "label", "");
    /**
     * property on name in the model.
     */
    private final StringProperty name
            = new SimpleStringProperty(this, "name", "");
    /**
     * property on type of plug.
     */
    private final StringProperty type
            = new SimpleStringProperty(this, "type", "other");

    /**
     * constructor.
     */
    public Plug() {
        super();
        init();
    }

    /**
     * initialize plug.
     */
    private void init() {
        this.getStylesheets().add(
                getClass().getResource("/gui/fxml/style/Plug.css")
                        .toExternalForm());
        this.getStyleClass().add("plug");

        this.setAlignment(Pos.CENTER);
        this.setPrefSize(25.0d, 25.0d);

        colorCircle = new Circle();
        colorCircle.setFill(Color.TRANSPARENT);
        colorCircle.setRadius(22.0d);
        colorCircle.setStroke(Type.getType(type.get()).color);
        colorCircle.setStrokeType(StrokeType.INSIDE);
        this.getChildren().add(colorCircle);

        colorCircle.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                workbench.plugClicked(this);
                event.consume();
            }
        });

        colorCircle.setOnMousePressed(Event::consume);
        colorCircle.setOnMouseDragged(Event::consume);


        nameLabel = new Label();
        getChildren().add(nameLabel);
        label.addListener((observable, oldValue, newValue) -> {
            nameLabel.setText(this.label.get());
        });
    }

    /**
     * @return center of plug in view.
     */
    public final Point2D getCenter() {
        return colorCircle.localToParent(
                colorCircle.getCenterX(), colorCircle.getCenterY());
    }

    /**
     * @return port of plug in the model
     */
    public final Port getPort() {
        if (getPortCommand != null) {
            try {
                return getPortCommand.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * setter on type of plug.
     * @param v to set
     */
    public final void setType(final String v) {
        type.set(v);
        colorCircle.setStroke(Type.getType(type.get()).color);
    }

    /**
     * @return type of plug
     */
    public final String getType() {
        return type.get();
    }

    /**
     * @return type property
     */
    public final StringProperty typeProperty() {
        return type;
    }

    /**
     * setter on name.
     * @return name of plug
     */
    public final String getName() {
        return name.get();
    }

    /**
     * @return property name
     */
    public final StringProperty nameProperty() {
        return name;
    }

    /**
     * setter on name.
     * @param newName to set
     */
    public final void setName(final String newName) {
        name.set(newName);
    }

    /**
     * @return label
     */
    public final String getLabel() {
        return label.get();
    }

    /**
     * @return label property
     */
    public final StringProperty labelProperty() {
        return label;
    }

    /**
     * setter on label.
     * @param newLabel to set
     */
    public final void setLabel(final String newLabel) {
        label.set(newLabel);
    }

    /**
     * @return cable connected
     */
    public final Cable getCable() {
        return cable;
    }

    /**
     * setter on cable.
     * @param newCable connect
     */
    public final void setCable(final Cable newCable) {
        cable = newCable;
    }
}
