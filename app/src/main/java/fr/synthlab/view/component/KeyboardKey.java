package fr.synthlab.view.component;

import fr.synthlab.view.controller.Workbench;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.logging.Logger;

public class KeyboardKey extends Rectangle {
    private static final Logger LOGGER = Logger.getLogger(KeyboardKey.class.getName());

    private Workbench workbench;

    private BooleanProperty isWhiteKey = new SimpleBooleanProperty(this, "isWhiteKey", true);

    public KeyboardKey() {
        super();

        this.setFill(Color.WHITE);
        this.setWidth(40);
        this.setHeight(150);
    }

    public final void setIsWhiteKey(final boolean whiteKey) {
        this.isWhiteKey.setValue(whiteKey);
        if (whiteKey) {
            this.setHeight(150);
            this.setFill(Color.WHITE);
        } else {
            this.setWidth(30);
            this.setHeight(110);
            this.setFill(Color.BLACK);
        }
    }

    public final BooleanProperty isWhiteKeyProperty() {
        return isWhiteKey;
    }

    public final boolean getIsWhiteKey() {
        return isWhiteKey.getValue();
    }
}
