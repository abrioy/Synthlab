package fr.synthlab.view.component;

import fr.synthlab.view.Workbench;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.logging.Logger;

public class KEYBKey extends Rectangle {
    private static final Logger logger = Logger.getLogger(KEYBKey.class.getName());

    private Workbench workbench;

    private BooleanProperty isWhiteKey = new SimpleBooleanProperty(this, "isWhiteKey", true);

    public KEYBKey() {
        super();

        this.setFill(Color.WHITE);
        this.setWidth(50);
        this.setHeight(140);
    }

    public final void setIsWhiteKey(boolean whiteKey) {
        this.isWhiteKey.setValue(whiteKey);
        if (whiteKey) {
            this.setHeight(140);
            this.setFill(Color.WHITE);
        } else {
            this.setHeight(100);
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
