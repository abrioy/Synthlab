package fr.synthlab.view.component;

import fr.synthlab.view.controller.Workbench;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.util.logging.Logger;

public class Plug extends Circle {
	private static final Logger logger = Logger.getLogger(Plug.class.getName());

	private Workbench workbench;
	public void setWorkbench(Workbench workbench) {
		this.workbench = workbench;
	}

    public enum Type {
        input(Color.DARKRED,22.0),
        output(Color.DARKGOLDENROD,22.0),
        other(Color.DARKBLUE,22.0);

        public Color color;
        public double size;

        Type(Color color, double size) {
            this.color = color;
            this.size = size;
        }

        public static Type getType(String name) {
            switch (name) {
                case "input":
                    return input;
                case "output":
                    return output;
            }
            return other;
        }
    }

	private final StringProperty name = new SimpleStringProperty(this, "name", "");
	private final StringProperty type = new SimpleStringProperty(this, "type", "other");

	public Plug() {
		super();
        init();

	}



    private void init() {
        this.setFill(Type.getType(type.get()).color);
        this.setRadius(Type.getType(type.get()).size);
        this.setStroke(Color.BLACK);
        this.setStrokeType(StrokeType.INSIDE);

        this.setOnMouseClicked(event -> {
            logger.info("CLICK");
            //workbench.plugClicked();

        });
        this.setOnMouseMoved(event -> {
            logger.info("MOVE IT MOVE IT");
            //workbench.plugClicked();

        });
    }

    public final void setType(String v) {
        type.set(v);
        this.setFill(Type.getType(type.get()).color);
        this.setRadius(Type.getType(type.get()).size);
    }

    public final String getType() {
        return type.get();
    }

    public final StringProperty typeProperty() {
        return type;
    }
}
