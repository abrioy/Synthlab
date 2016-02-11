package fr.synthlab.view.component;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.util.logging.Logger;


public class PickColor extends ColorPicker{
    private static final Logger logger = Logger.getLogger(PickColor.class.getName());
    private static Color c;

    public static Color newColor () {
        final ColorPicker colorPicker = new ColorPicker();
        return colorPicker.getValue();
    }
}