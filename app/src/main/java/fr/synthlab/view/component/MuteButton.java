package fr.synthlab.view.component;

import javafx.scene.control.Button;

/**
 * Mute button.
 */
public class MuteButton extends Button {

    /**
     * constructor.
     */
    public MuteButton() {
        this.getStylesheets().add(
                getClass().getResource("/gui/fxml/style/Module.css")
                        .toExternalForm());
        this.getStyleClass().add("mute-release");
    }

    /**
     * action.
     * @param toggle new state of button
     */
    public final void setToggle(final boolean toggle) {
        this.getStyleClass().clear();
        if (toggle) {
            this.getStyleClass().add("mute-pressed");
        } else {
            this.getStyleClass().add("mute-release");
        }
    }
}
