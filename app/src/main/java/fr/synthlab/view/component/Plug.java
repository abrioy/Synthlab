package fr.synthlab.view.component;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.logging.Logger;

public class Plug extends Circle {
	private static final Logger logger = Logger.getLogger(Plug.class.getName());

	public Plug() {
		super();

		this.setFill(Color.BEIGE);
		this.setRadius(10.0f);
	}
}
