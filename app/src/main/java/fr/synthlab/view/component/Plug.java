package fr.synthlab.view.component;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.apache.log4j.Logger;

public class Plug extends Circle {
	private static final Logger logger = Logger.getLogger(Plug.class);

	public Plug() {
		super();

		this.setFill(Color.BEIGE);
		this.setRadius(10.0f);
	}
}
