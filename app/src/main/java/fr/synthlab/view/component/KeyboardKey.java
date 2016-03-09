package fr.synthlab.view.component;

import fr.synthlab.view.controller.workbench.Workbench;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.logging.Logger;

/**
 * draw keyboard key.
 */
public class KeyboardKey extends Rectangle {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(KeyboardKey.class.getName());

    /**
     * workbench.
     */
    private Workbench workbench;

    /**
     * is white property.
     */
    private BooleanProperty isWhiteKey
            = new SimpleBooleanProperty(this, "isWhiteKey", true);

    /**
     * constructor.
     */
    public KeyboardKey() {
        super();
        setIsWhiteKey(true);
    }

    /**
     * setter on white property value.
     * @param whiteKey value to set
     */
    public final void setIsWhiteKey(final boolean whiteKey) {
        this.isWhiteKey.setValue(whiteKey);
        if (whiteKey) {
            this.setWidth(40);
            this.setHeight(150);
            this.setFill(Color.WHITE);
        } else {
            this.setWidth(30);
            this.setHeight(110);
            this.setFill(Color.BLACK);
        }
    }

    /**
     * @return white property
     */
    public final BooleanProperty isWhiteKeyProperty() {
        return isWhiteKey;
    }

    /**
     * @return white property value
     */
    public final boolean getIsWhiteKey() {
        return isWhiteKey.getValue();
    }
}
