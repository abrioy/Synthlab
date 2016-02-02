package fr.synthlab.view.component;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.apache.log4j.Logger;

public class Plug extends Circle {
	private static final Logger logger = Logger.getLogger(Plug.class);
	public Plug() {
		super();
		this.setFill(Color.DARKRED);
		this.setRadius(10.0f);
	}
    public Plug(double centerX, double centerY) {
        super(centerX,centerY,10.0f);
        this.setFill(Color.DARKRED);
    }
}
