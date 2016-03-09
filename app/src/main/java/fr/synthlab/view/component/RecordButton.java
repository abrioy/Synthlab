package fr.synthlab.view.component;

import javafx.scene.control.Button;

/**
 * Record Button.
 */
public class RecordButton extends Button {
    /**
     * constructor.
     */
    public RecordButton() {
        this.getStylesheets().add(
                getClass().getResource("/gui/fxml/style/Module.css")
                        .toExternalForm());
        this.getStyleClass().add("record-release");
    }

    /**
     * action.
     * @param toggle new state of button
     */
    public final void setToggle(final boolean toggle) {
        this.getStyleClass().clear();
        if (toggle) {
            this.getStyleClass().add("record-pressed");
        } else {
            this.getStyleClass().add("record-release");
        }
    }
}
