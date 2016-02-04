package fr.synthlab.view.component;

import fr.synthlab.model.module.port.Port;
import fr.synthlab.view.Workbench;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class Plug extends Circle {
	private static final Logger logger = Logger.getLogger(Plug.class.getName());

	private Workbench workbench;
	public void setWorkbench(Workbench workbench) {
		this.workbench = workbench;
	}

	private Callable<Port> getPortCommand = null;
	public void setGetPortCommand(Callable<Port> getPortCommand) {
		this.getPortCommand = getPortCommand;
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

        this.setOnMouseReleased(event -> {
			workbench.plugClicked(this);
        });
    }

	public Port getPort() {
		if(getPortCommand != null){
			try {
				return getPortCommand.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
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


	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}
}
