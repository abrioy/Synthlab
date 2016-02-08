package fr.synthlab.view.component;

import fr.synthlab.model.module.port.Port;
import fr.synthlab.view.Workbench;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class Plug extends StackPane {
	private static final Logger logger = Logger.getLogger(Plug.class.getName());

	private Circle colorCircle;

	private Workbench workbench;
	public void setWorkbench(Workbench workbench) {
		this.workbench = workbench;
	}

	private Callable<Port> getPortCommand = null;
	public void setGetPortCommand(Callable<Port> getPortCommand) {
		this.getPortCommand = getPortCommand;
	}

	public enum Type {
        input(Color.DARKCYAN),
        output(Color.DARKGREEN),
        other(Color.DARKSALMON);

        public Color color;

        Type(Color color) {
            this.color = color;
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
		this.getStylesheets().add(
				getClass().getResource("/gui/fxml/style/Plug.css").toExternalForm());
		this.getStyleClass().add("plug");

		this.setAlignment(Pos.CENTER);
		this.setPrefSize(25.0d, 25.0d);

		colorCircle = new Circle();
        colorCircle.setFill(Color.TRANSPARENT);
        colorCircle.setRadius(22.0d);
        colorCircle.setStroke(Type.getType(type.get()).color);
        colorCircle.setStrokeType(StrokeType.INSIDE);
		this.getChildren().add(colorCircle);

        this.setOnMouseClicked(event -> {
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
        colorCircle.setStroke(Type.getType(type.get()).color);
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
