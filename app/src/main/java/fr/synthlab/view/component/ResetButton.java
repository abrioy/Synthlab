package fr.synthlab.view.component;

import javafx.scene.control.Button;

/**
 * Reset button for sequencer.
 */
public class ResetButton extends Button {
    /**
     * constructor.
     */
    public ResetButton() {
        this.getStylesheets().add(
                getClass().getResource("/gui/fxml/style/Module.css")
                        .toExternalForm());
        this.getStyleClass().add("reset-button");
    }
}
